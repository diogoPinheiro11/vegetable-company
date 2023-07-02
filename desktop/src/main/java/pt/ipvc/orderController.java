package pt.ipvc;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.stage.Modality;
import pt.ipvc.bll.OrdersBLL;
import pt.ipvc.bll.ProductionBLL;
import pt.ipvc.bll.RoleBLL;
import pt.ipvc.bll.UsersBLL;
import pt.ipvc.dal.Orders;
import pt.ipvc.dal.Production;
import pt.ipvc.dal.Role;
import pt.ipvc.dal.Users;

import java.io.IOException;
import java.util.*;

public class orderController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TableView<Orders> dataView;
    @FXML
    private TableColumn<Orders, String> productOrderColumn;
    @FXML
    private TableColumn<Orders, String> clientOrderColumn;
    @FXML
    private TableColumn<Orders, String> priceOrderColumn;
    @FXML
    private TableColumn<Orders, String> quantityOrderColumn;
    @FXML
    private TableColumn<Orders, String> dateStartOrderColum;
    @FXML
    private TableColumn<Orders, String> dateEndOrderColum;
    @FXML
    private TableColumn<Orders, String> stateOrderColumn;

    public void initialize() {
        ObservableList<String> tOrder = FXCollections.observableArrayList();

        List<Orders> orders = OrdersBLL.index();
        Collections.sort(orders, Comparator.comparingInt(orders1 -> orders1.getId()));
        ObservableList<Orders> data = FXCollections.observableArrayList(orders);

        dataView.setItems(data);
        productOrderColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getIdStock())));
        clientOrderColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getIdClient())));
        priceOrderColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getOrderPrice())));
        quantityOrderColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getOrderQuantity())));
        dateStartOrderColum.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getDateStart())));
        dateEndOrderColum.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getDateEnd())));
        stateOrderColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getState())));
    }


    @FXML
    public void onHomeButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onUsersButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("user.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onProductsButtonClick(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("product.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onOrdersButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("order.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onLogoutButtonClick(ActionEvent event) throws IOException{
        ButtonType continueButtonType = new ButtonType("Continue", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the application?", continueButtonType, cancelButtonType);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        DialogPane alertDialog = alert.getDialogPane();
        alertDialog.getStyleClass().add("alert");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == continueButtonType) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login_example.fxml")));
            Scene loginScene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(loginScene);
            stage.show();
        }
    }
    @FXML
    public void onAddOrderButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("order_add.fxml"));
        Parent root = fxmlLoader.load();
        Scene popupScene = new Scene(root);
        Stage popupStage = new Stage();
        popupStage.setScene(popupScene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Adding Order..");
        popupStage.setResizable(false);
        popupStage.show();
    }

    public void onEditOrderButtonClick(ActionEvent event) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("order_edit.fxml"));
        Parent root = fxmlLoader.load();
        Scene popupScene = new Scene(root);
        Stage popupStage = new Stage();
        popupStage.setScene(popupScene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Adding Order..");
        popupStage.setResizable(false);
        popupStage.show();*/
    }
    public void onListTotalBilledButtonClick(ActionEvent event) throws IOException {

    }

    private void updateDataView(List<Orders> orders) {
        Collections.sort(orders, Comparator.comparingInt(orders1 -> orders1.getId()));
        ObservableList<Orders> data = FXCollections.observableArrayList(orders);
        dataView.setItems(data);
        dataView.refresh();
    }
}
