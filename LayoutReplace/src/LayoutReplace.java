import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class LayoutReplace extends Application {
    private static char[] ukrChars = {'й', 'ц', 'у', 'к', 'е', 'н', 'г', 'ш', 'щ', 'з', 'х', 'ї',
            'ф', 'і', 'в', 'а', 'п', 'р', 'о', 'л', 'д', 'ж', 'є',
            'я', 'ч', 'с', 'м', 'и', 'т', 'ь', 'б', 'ю', '.',

            '\'', 'Х', 'Ї', 'Ж', 'Є', 'Б', 'Ю', ',', '₴', '/', '\"', '№', ';', ':', '?'};

    private static char[] engChars = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '[', ']',
            'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ';', '\'',
            'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/',

            '`', '{', '}', ':', '\"', '<', '>', '?', '~', '|', '@', '#', '$', '^', '&' };


    private static int indexOf(char[] arr, char c) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == c) return i;
        }
        return -1;
    }

    private static void replace(char[] fromArr, char[] toArr) {
        boolean isUpper;
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        String text = clipboard.getString();
        if (text == null || text.equals("")) return;
        char[] charArr = text.toCharArray();
        for (int i = 0; i < charArr.length; i++) {
            if (charArr[i] == '\n' || charArr[i] == '\r') continue;
            isUpper = false;
            if (Character.isUpperCase(charArr[i]) && indexOf(fromArr, charArr[i]) == -1) {
                isUpper = true;
                charArr[i] = Character.toLowerCase(charArr[i]);
            }
            int index = indexOf(fromArr, charArr[i]);
            if (index != -1) charArr[i] = toArr[index];
            if (isUpper) charArr[i] = Character.toUpperCase(charArr[i]);
        }
        final ClipboardContent content = new ClipboardContent();
        content.putString(String.valueOf(charArr));
        clipboard.setContent(content);
    }

    public static void main(String[] args) { launch(args); }

    public void start(Stage appStage) {
        appStage.setTitle("Layout Replace");
        appStage.getIcons().add(new Image("file:icon.png"));
        BorderPane rootNode = new BorderPane();
        Scene appScene = new Scene(rootNode, 250, 250);
        appStage.setScene(appScene);

        FlowPane buttonsPane = new FlowPane(Orientation.VERTICAL);
        buttonsPane.setAlignment(Pos.CENTER);
        buttonsPane.setColumnHalignment(HPos.CENTER);
        //buttonsPane.setPrefWrapLength(150);         // preferred width
        buttonsPane.setVgap(20);
        rootNode.setCenter(buttonsPane);

        Button buttonEngToUkr = new Button("Eng -> Ukr");
        buttonEngToUkr.setContentDisplay(ContentDisplay.CENTER);
        buttonEngToUkr.setStyle("-fx-font-size: 12pt;");
        buttonEngToUkr.setOnAction( (ae) -> replace(engChars, ukrChars) );

        Button buttonUkrToEng = new Button("Ukr -> Eng");
        buttonUkrToEng.setContentDisplay(ContentDisplay.CENTER);
        buttonUkrToEng.setStyle("-fx-font-size: 12pt;");
        buttonUkrToEng.setOnAction( (ae) -> replace(ukrChars, engChars) );

        buttonsPane.getChildren().addAll(buttonEngToUkr, buttonUkrToEng);
        appStage.show();
    }
}