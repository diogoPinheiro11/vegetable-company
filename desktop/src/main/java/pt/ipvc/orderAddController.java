package pt.ipvc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class orderAddController {
    @FXML
    public void onAddOrderButtonClick(ActionEvent event){
        System.out.println("Add Button was Clicked");
    }
    @FXML
    public void onCancelButtonClick(ActionEvent event){
        System.out.println("Cancel Button was Clicked");

    }
}
