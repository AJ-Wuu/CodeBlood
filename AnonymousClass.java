/**
 * @author AJWuu
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CustomTextFieldSample extends Application {
    
    /*
     * Reasons to use Anonymous Functions: 
     * 1. The function is only ever called in one place
     * 2. Declared inline and inline functions have advantages in that they can access variables in the parent scopes
     * 3. More self-contained and readable
     *
     * In Java, a class can contain another class known as nested class.
     * A nested class that doesn't have any name is known as an anonymous class.
     * An anonymous class must be defined inside another class.
     * Hence, it is also known as an anonymous inner class.
     * 
     * Syntax: 
     * class outerClass {
     *     // defining anonymous class
     *     object1 = new Type(parameterList) { //Type can be a superclass that an anonymous class extends OR an interface that an anonymous class implements
     *         // body of the anonymous class
     *     };
     * }
     * 
     * Reasons to use Anonymous Classes:
     * 1. A particular class A is the only consumer for a Class B and no where else this B class can be used
     * 2. Easy to maintain
     * 3. Reduce verbosity (make the code more concise) 
     * 4. Enable you to declare and instantiate a class at the same time
     */
    
    final static Label label = new Label();
 
    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 300, 150);
        stage.setScene(scene);
        stage.setTitle("Text Field Sample");
 
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
 
        scene.setRoot(grid);
        final Label dollar = new Label("$");
        GridPane.setConstraints(dollar, 0, 0);
        grid.getChildren().add(dollar);
        
        final TextField sum = new TextField() {
            @Override
            public void replaceText(int start, int end, String text) {
                if (!text.matches("[a-z, A-Z]")) {
                    super.replaceText(start, end, text);                     
                }
                label.setText("Enter a numeric value");
            }
 
            @Override
            public void replaceSelection(String text) {
                if (!text.matches("[a-z, A-Z]")) {
                    super.replaceSelection(text);
                }
            }
        };
 
        sum.setPromptText("Enter the total");
        sum.setPrefColumnCount(10);
        GridPane.setConstraints(sum, 1, 0);
        grid.getChildren().add(sum);
        
        Button submit = new Button("Submit");
        GridPane.setConstraints(submit, 2, 0);
        grid.getChildren().add(submit);
        
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                label.setText(null);
            }
        });
        
        GridPane.setConstraints(label, 0, 1);
        GridPane.setColumnSpan(label, 3);
        grid.getChildren().add(label);
        
        scene.setRoot(grid);
        stage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}
