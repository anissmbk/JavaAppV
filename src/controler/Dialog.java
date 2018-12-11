package controler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class Dialog {

    public static void loadDialog(String usernameRegister , StackPane mainPane){
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("welcome!!"));
        content.setBody(new Text("Bonjour " + usernameRegister + " : \n"
                + "Vous venez de vous inscrire en SayHi.............."));
        JFXDialog dialog = new JFXDialog(mainPane,content,JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("Okay");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        content.setActions(button);
        dialog.show();
    }
}

