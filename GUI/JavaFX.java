/**
 * GUI: Graphic User Interfaces, interfaces that users interact with asynchronously
 *
 * Basic Steps for JavaFX Application
 *     class JavaFXApp extends javafx application
 *     main() method calls launch()
 *     launch() initializes JavaFX, then calls start(stage)
 *     start(Stage mainStage) sets up GUI on stage
 *         -- create controls and place them into layout managers
 *         -- place layout managers into scene
 *         -- place scene into stage
 *         -- mainStage.show()
 *
 * Interface Controls (GUI elements, import javafx.scene.controls.*)
 * Label label = new Label("user name: ");
 * Button button = new Button("submit");
 * TextField -> get user input as a String
 * TextArea -> get paragraph of text from user
 * ImageView -> display images for users
 * ComboBox -> show multiple options in a drop-down list
 
 */
