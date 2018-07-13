package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.csv.MyCSVReader;
import sample.csv.MyCSVWriter;
import sample.graph.Graph;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button save_button;

    @FXML
    private Button load_button;

    @FXML
    private Button calculate_button;

    @FXML
    private TextField formula_field;

    @FXML
    private Label coords_lable;

    public static boolean isRoot = false;

    @FXML
    void initialize() {
        calculate_button.setOnAction(event -> {
            Roots roots;
            String formula = formula_field.getText();
            if (isCorrect(formula)) {
                float[] values = getValues(formula);
                roots = getRoots(values);
                for(float i : values){
                    System.out.println(i);
                }
                coords_lable.setText(getStringRoots(roots));
                if(isRoot){
                    setGraph(values);
                }
            } else {
                showMessage("Error", "!!!Ошибка ввода!!!\n Пример формулы: 2x^2+2x+2=0.");
            }
        });
        save_button.setOnAction(event -> {
            Roots roots;
            String formula = formula_field.getText();
            if (isCorrect(formula)) {
                float[] values = getValues(formula);
                roots = getRoots(values);
                coords_lable.setText(getStringRoots(roots));
                if(isRoot){
                    String[] entries = (formula_field.getText() +"#" + roots.x1 + "#"+ roots.x2).split("#");
                    MyCSVWriter myCSVWriter = new MyCSVWriter();
                    myCSVWriter.write(entries);
                }
            } else {
                showMessage("Error", "!!!Ошибка ввода!!!\n Пример формулы: 2x^2+2x+2=0.");
            }

        });

        load_button.setOnAction(event -> {
            MyCSVReader myCSVReader = new MyCSVReader();
            String [] values = myCSVReader.read();
            if(values.length == 3){
                formula_field.setText(values[0]);
                Roots roots = new Roots();
                roots.x1 = Float.valueOf(values[1]);
                roots.x2 = Float.valueOf(values[2]);
                coords_lable.setText(getStringRoots(roots));
            }
        });
    }

    private String getStringRoots(Roots roots) {
        return String.format("x1 = %1$s; x2 = %2$s", roots.x1, roots.x2);
    }

    private void showMessage(String title, String mes) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(mes);
        alert.showAndWait();
    }

    private Roots getRoots(float[] arr) {
        Roots roots = new Roots();
        float a = arr[0];
        float b = arr[1];
        float c = arr[2];
        float d = (b * b) - (4 * a * c);
        if (d < 0){
            showMessage("Error", "Нет корней!");
            isRoot = false;
        }
        else if (d == 0) {
            isRoot = true;
            roots.x1 = (-b) / (2 * a);
            System.out.println("x = " + roots.x1);
        } else {
            roots.x1 = (float) (-b + Math.sqrt(d)) / (2 * a);
            roots.x2 = (float) (-b - Math.sqrt(d)) / (2 * a);
        }
        return roots;
    }

    private Boolean isCorrect(String str) {
        Pattern p = Pattern.compile("^-?([1-9][0-9]+[xXхХ]|[2-9][xXхХ]|[xXхХ])*\\^2[+\\-]([1-9][0-9]+[xXхХ]|[2-9][xXхХ]|[xXхХ])([+\\-]\\d+)*=0$");
        Matcher m = p.matcher(str);
        if(m.matches()) return true;
        p = Pattern.compile("^-?([1-9][0-9]+[xXхХ]|[2-9][xXхХ]|[xXхХ])*\\^2([+\\-]\\d+)*=0$");
        m = p.matcher(str);
        return m.matches();
    }

    private float[] getValues(String str) {
        float[] res = new float[3];
        Pattern p = Pattern.compile("^([-]?\\d+)*[xXхХ]\\^2([+\\-]\\d*)*[xXхХ]([+\\-]\\d+)*=0$");
        Matcher m = p.matcher(str);
        if (m.matches()) {
            if(m.group(1) == null) res[0] = 1;
            else res[0] = Float.parseFloat(m.group(1));
            if(m.group(2).equals("+") || m.group(2).equals("-")) res[1] = 1;
            else res[1] = Float.parseFloat(m.group(2));
            if(m.group(3) == null) res[2] = 0;
            else res[2] = Float.parseFloat(m.group(3));
            return res;
        }
        p = Pattern.compile("^([-]?\\d+)*[xXхХ]\\^2([+\\-]\\d+)*=0$");
        m = p.matcher(str);
        if (m.matches()) {
            if(m.group(1) == null) res[0] = 1;
            else res[0] = Float.parseFloat(m.group(1));
            res[1] = 0;
            if(m.group(2) == null) res[2] = 0;
            else res[2] = Float.parseFloat(m.group(2));
            return res;
        }
        return res;
    }

    class Roots {
        float x1;
        float x2;
    }


    private void setGraph(float[] values) {
        Graph graph = new Graph();
        graph.setGraphic(values);
    }

}

