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
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.ipvc.bll.SeedsBLL;
import pt.ipvc.bll.UsersBLL;
import pt.ipvc.dal.Seeds;
import pt.ipvc.dal.Users;
import java.sql.Date;


import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


public class seedController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField descriptionFilter;
    @FXML
    private TableView<Seeds> dataView;
    @FXML
    private TableColumn<Seeds, String> descriptionSeedsColumn;
    @FXML
    private TableColumn<Seeds, String> quantitySeedsColumn;
    @FXML
    private TableColumn<Seeds, String> dateSeedsColumn;
    @FXML
    private TableColumn<Seeds, String> supplierSeedsColumn;

    public void initialize() {
        ObservableList<String> tSeed = FXCollections.observableArrayList();

        List<Seeds> seeds = SeedsBLL.index();
        Collections.sort(seeds, Comparator.comparingInt(seeds1 -> seeds1.getId()));
        ObservableList<Seeds> data = FXCollections.observableArrayList(seeds);

        dataView.setItems(data);
        descriptionSeedsColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getDescription())));
        quantitySeedsColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getQuantityRequested())));
        dateSeedsColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getDate())));
        supplierSeedsColumn.setCellValueFactory(d -> {
            int supplierId = d.getValue().getIdSupplier();
            List<Users> suppliers = UsersBLL.getByRole("supplier");
            String supplierName = "";
            for (Users supplier : suppliers) {
                if (supplier.getId() == supplierId) {
                    supplierName = supplier.getName();
                    break;
                }
            }
            return new SimpleStringProperty(supplierName);
        });

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
    public void onProductsButtonClick(ActionEvent event) throws IOException {
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
    public void onAddSeedsButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("seed_add.fxml"));
        Parent root = fxmlLoader.load();
        Scene popupScene = new Scene(root);
        Stage popupStage = new Stage();
        popupStage.setScene(popupScene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Adding Seed..");
        popupStage.setResizable(false);
        popupStage.show();
        List<Seeds> seeds = SeedsBLL.index();
        updateDataView(seeds);
    }
    @FXML
    public void onEditSeedsButtonClick(ActionEvent event) throws IOException {
        Seeds selectedSeed = dataView.getSelectionModel().getSelectedItem();
        if (selectedSeed != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("seed_edit.fxml"));
            Parent parent = loader.load();
            seedEditController controller = loader.getController();
            controller.setSeed(dataView.getSelectionModel().getSelectedItem());
            Scene scene = new Scene(parent);
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            dialogStage.setTitle("Editing Seed..");
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
            dataView.refresh();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edit Seed");
            alert.setHeaderText("You must select one seed to edit");

            ButtonType okButton = new ButtonType("Continue", ButtonBar.ButtonData.OK_DONE);

            alert.getButtonTypes().setAll(okButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton) {
                alert.close();
            }
        }
    }

    @FXML
    public void filterSeeds(KeyEvent event) {
        String filter = descriptionFilter.getText().toLowerCase();
        List<Seeds> seeds = SeedsBLL.index();
        List<Seeds> filteredSeeds = seeds.stream()
                .filter(seed -> seed.getDescription().toLowerCase().contains(filter))
                .collect(Collectors.toList());
        updateDataView(filteredSeeds);
    }

    private void updateDataView(List<Seeds> seeds) {
        Collections.sort(seeds, Comparator.comparingInt(seeds1 -> seeds1.getId()));
        ObservableList<Seeds> data = FXCollections.observableArrayList(seeds);
        dataView.setItems(data);
        dataView.refresh();
    }
}
