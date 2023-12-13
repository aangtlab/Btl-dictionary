package com.example.btl_2023;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.nio.file.Path;
import java.io.FileWriter;
import java.util.function.Function;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;
import javafx.util.Pair;

public class MainApp extends Application {

    private final DictionaryManagement dictionaryManagement = new DictionaryManagement(new Dictionary());
    private final TabPane tabPane = new TabPane();
    private final Dictionary dictionary = new Dictionary();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        if (showLoginDialog()) {
        primaryStage.setTitle("Dictionary");
        primaryStage.getIcons().add(new Image("file:C:/Users/Admin/IdeaProjects/BTL_2023/src/main/java/com/example/btl_2023/images/logotudien.jpg"));

        // Tạo UI components
        Button lookupButton = new Button("Tìm Kiếm");
        Button addWordButton = new Button("Thêm Từ");
        Button editWordButton = new Button("Sửa Từ");
        Button deleteWordButton = new Button("Xóa Từ");
        Button translateButton = new Button("Dịch");
        Button gameButton = new Button("Trò Chơi");
        //Button exportButton = new Button("Export");
        Button exitButton = new Button("Thoát");


        // Thiết lập hành động cho các nút
        lookupButton.setOnAction(e -> showLookupTab());
        translateButton.setOnAction(e -> showTranslateTab());
        gameButton.setOnAction(e -> showGameTab());
        addWordButton.setOnAction(e -> showAddWordTab());
        editWordButton.setOnAction(e -> showEditWordTab());
        deleteWordButton.setOnAction(e -> showDeleteWordTab());
        //exportButton.setOnAction(e -> dictionaryManagement.dictionaryExportToFile());
        exitButton.setOnAction(e -> primaryStage.close());


        // Layout sử dụng HBox để đặt logo bên phải của label chính
        HBox headerBox = new HBox(10);
        headerBox.setAlignment(Pos.TOP_CENTER);
        //"C:\Users\Admin\IdeaProjects\BTL_2023\src\main\java\com\example\btl_2023\images\cambridge.jpg"
        // Tạo ImageView cho logo
        ImageView logoImageView1 = new ImageView(new Image("file:C:/Users/Admin/IdeaProjects/BTL_2023/src/main/java/com/example/btl_2023/images/logotudien.jpg"));
        logoImageView1.setFitWidth(100);
        logoImageView1.setFitHeight(100);

        //Tạo một ảnh Cambridge
        ImageView logoImageView2 = new ImageView(new Image("file:C:/Users/Admin/IdeaProjects/BTL_2023/src/main/java/com/example/btl_2023/images/cambridge.jpg"));
        logoImageView2.setFitWidth(200);
        logoImageView2.setFitHeight(100);

        // Tạo label chính
        Label mainLabel = new Label("\n-Chào Mừng em đến với nhà của bọn anh \n-Các anh ở đây à \n-Ồ bọn anh sống đâu chả được, cứ thoải mái đi! ");
        mainLabel.getStyleClass().add("main-label"); // Thêm kiểu CSS cho label chính
        mainLabel.setFont(Font.font("Arial", FontPosture.ITALIC, 14));

        // Thêm ImageView và label vào HBox
        headerBox.getChildren().addAll(logoImageView2, mainLabel);
        headerBox.setId("headerBox");


        // Layout chứa các nút và headerBox
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getStyleClass().add("main-layout"); // Thêm kiểu CSS cho layout
        layout.getChildren().addAll(
                headerBox, lookupButton, addWordButton, editWordButton, deleteWordButton,
                translateButton, gameButton, exitButton, tabPane
        );

        // Load CSS
        Scene scene = new Scene(layout, 700, 750);
        layout.getStylesheets().add("file:/C:/Users/Admin/IdeaProjects/BTL_2023/src/main/java/com/example/btl_2023/styles.css");


        primaryStage.setScene(scene);

        // Load dữ liệu từ điển khi khởi động ứng dụng
        dictionaryManagement.insertFromFile();

        showLookupTab();

        primaryStage.show();}
        else {
            primaryStage.close();
        }
    }



    private void showLookupTab() {
        Tab lookupTab = new Tab("Tìm Kiếm");
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(15));

        // Label, TextField và các nút tìm kiếm, phát âm
        Label label = new Label("Nhập từ bạn muốn tìm kiếm: ");
        TextField inputField = new TextField();
        Button searchButton = new Button("Tìm kiếm");
        Button pronounceButton = new Button("Phát âm");
        ListView<String> resultListView = new ListView<>();

        // Hiển thị tất cả các từ khi mở tab
        List<String> allWords = dictionaryManagement.getAllWords();
        resultListView.getItems().addAll(allWords);

        // Hàm loại bỏ dấu cách ở đầu từ
        Function<String, String> removeLeadingSpaces = input -> input.trim();

        // Thêm sự kiện khi nhấn nút tìm kiếm
        searchButton.setOnAction(e -> {
            String wordToLookup = removeLeadingSpaces.apply(inputField.getText());
            if (!wordToLookup.isEmpty()) {
                List<String> lookupResults = dictionaryManagement.dictionaryLookup(wordToLookup);
                resultListView.getItems().clear();
                if (lookupResults.isEmpty()) {
                    showAlert("Thông báo", "Từ không tồn tại trong từ điển.");
                } else {
                    resultListView.getItems().addAll(lookupResults);
                }
            } else {
                // Nếu trường nhập liệu rỗng, hiển thị tất cả các từ
                resultListView.getItems().clear();
                resultListView.getItems().addAll(allWords);
            }
        });

        // Thêm sự kiện khi nhấn nút phát âm
        pronounceButton.setOnAction(e -> {
            String wordToPronounce = removeLeadingSpaces.apply(inputField.getText());
            dictionaryManagement.pronounceWord(wordToPronounce);
        });

        // Thêm các thành phần vào VBox
        vbox.getChildren().addAll(label, inputField, searchButton, pronounceButton, resultListView);
        lookupTab.setContent(vbox);

        // Thêm tab vào TabPane
        tabPane.getTabs().add(lookupTab);
        // Chọn tab vừa được thêm
        tabPane.getSelectionModel().select(lookupTab);
    }




    // Hàm hiển thị cửa sổ thông báo
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void showTranslateTab() {
        Tab translateTab = new Tab("Dịch");
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(15));

        Label label = new Label("Nhập từ cần dịch (Tiếng Anh): ");
        TextField inputField = new TextField();
        Button translateButton = new Button("Dịch");

        // Sử dụng TextArea để hiển thị nghĩa dịch
        TextArea translationArea = new TextArea();
        translationArea.setEditable(false); // Ngăn người dùng chỉnh sửa nội dung

        translateButton.setOnAction(e -> {
            String wordToTranslate = inputField.getText();
            if (!wordToTranslate.isEmpty()) {
                String translatedText = translateWithGoogleAPI(wordToTranslate);
                translationArea.setText(translatedText);
            } else {
                translationArea.setText("Vui lòng nhập từ cần dịch.");
            }
        });

        vbox.getChildren().addAll(label, inputField, translateButton, translationArea);

        // Thêm VBox vào ScrollPane để có khả năng cuộn
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        translateTab.setContent(scrollPane);

        // Thêm tab vào TabPane
        tabPane.getTabs().add(translateTab);
        // Chọn tab vừa được thêm
        tabPane.getSelectionModel().select(translateTab);
    }



    private String translateWithGoogleAPI(String wordToTranslate) {
        try {
            return TranslatorAPIwithScripts.translate("en", "vi", wordToTranslate);
        } catch (IOException e) {
            e.printStackTrace();
            return "Đã xảy ra lỗi khi dịch từ.";
        }
    }

    private void showAddWordTab() {
        Tab addWordTab = new Tab("Thêm từ");
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(15));

        // Components for adding a word
        Label wordLabel = new Label("Từ mới:");
        TextField wordField = new TextField();
        Label meaningLabel = new Label("Nghĩa:");
        TextField meaningField = new TextField();
        Button addButton = new Button("Thêm từ");

        addButton.setOnAction(e -> {
            String word = wordField.getText();
            String meaning = meaningField.getText();
            if (!word.isEmpty() && !meaning.isEmpty()) {
                // Add the word to the dictionary
                dictionaryManagement.addWord(word, meaning);
                // Export the dictionary to the file
                dictionaryManagement.dictionaryExportToFile();
                // Clear the input fields
                wordField.clear();
                meaningField.clear();
                // Display a message (you can customize this part)
                showAlert("Thông báo", "Thêm từ thành công!");
            } else {
                // Display an error message (you can customize this part)
                showAlert("Lỗi", "Vui lòng nhập đầy đủ từ và nghĩa.");
            }
        });

        vbox.getChildren().addAll(wordLabel, wordField, meaningLabel, meaningField, addButton);
        addWordTab.setContent(vbox);

        // Add the new tab to the TabPane
        tabPane.getTabs().add(addWordTab);
        // Select the newly added tab
        tabPane.getSelectionModel().select(addWordTab);
    }


    private void showEditWordTab() {
        Tab editWordTab = new Tab("Sửa Từ");
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(15));

        Label titleLabel = new Label("Sửa từ trong từ điển");
        Label wordLabel = new Label("Nhập từ Tiếng Anh cần sửa:");
        TextField wordField = new TextField();
        Label explainLabel = new Label("Nhập giải thích Tiếng Việt mới:");
        TextField explainField = new TextField();
        Button editButton = new Button("Sửa từ");

        editButton.setOnAction(e -> handleEditWord(wordField.getText(), explainField.getText()));

        vbox.getChildren().addAll(titleLabel, wordLabel, wordField, explainLabel, explainField, editButton);
        editWordTab.setContent(vbox);

        // Add the new tab to the TabPane
        tabPane.getTabs().add(editWordTab);
        // Select the newly added tab
        tabPane.getSelectionModel().select(editWordTab);
    }

    private void handleEditWord(String wordTarget, String newExplain) {
        if (dictionaryManagement.editWord(wordTarget, newExplain)) {
            // Nếu sửa từ thành công, hiển thị cửa sổ thông báo
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Sửa từ thành công");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Đã sửa từ '" + wordTarget + "' thành công!");
            successAlert.showAndWait();

            // Sau khi hiển thị thông báo, cập nhật giao diện
            //showLookupTab(); // Hoặc bất kỳ phương thức nào khác để cập nhật giao diện

            // Xuất dữ liệu từ điển ra tệp (lưu vào cơ sở dữ liệu)
            dictionaryManagement.dictionaryExportToFile();
        } else {
            // Nếu không tìm thấy từ để sửa, hiển thị cửa sổ thông báo lỗi
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Lỗi");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Không tìm thấy từ '" + wordTarget + "' trong từ điển.");
            errorAlert.showAndWait();
        }
    }



    private void showDeleteWordTab() {
        Tab deleteWordTab = new Tab("Xóa Từ");
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(15));

        Label label = new Label("Nhập từ Tiếng Anh cần xóa: ");
        TextField inputField = new TextField();
        Button deleteButton = new Button("Xóa từ");

        deleteButton.setOnAction(e -> handleDeleteWord(inputField.getText()));

        vbox.getChildren().addAll(label, inputField, deleteButton);
        deleteWordTab.setContent(vbox);

        // Add the new tab to the TabPane
        tabPane.getTabs().add(deleteWordTab);
        // Select the newly added tab
        tabPane.getSelectionModel().select(deleteWordTab);
    }

    private void handleDeleteWord(String wordTarget) {
        if (!wordTarget.isEmpty()) {
            boolean isDeleted = dictionaryManagement.removeWord(wordTarget);
            if (isDeleted) {
                showSuccessAlert("Đã xóa từ '" + wordTarget + "' khỏi từ điển.");
                dictionaryManagement.dictionaryExportToFile();
            } else {
                showAlert("Không tìm thấy từ '" + wordTarget + "' trong từ điển.", Alert.AlertType.WARNING);
            }
        } else {
            showAlert("Vui lòng nhập từ cần xóa.", Alert.AlertType.WARNING);
        }
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void exportDictionaryToFile() {
        try {
            // Lấy đường dẫn tuyệt đối của thư mục làm việc và kết hợp với tên tệp
            Path currentWorkingDirectory = Path.of("").toAbsolutePath();
            Path filePath = currentWorkingDirectory.resolve("src/main/java/com/example/btl_2023/dictionaries.txt");

            // Tạo đối tượng FileWriter để ghi dữ liệu vào tệp
            FileWriter fileWriter = new FileWriter(filePath.toString());

            // Lấy danh sách từ điển
            List<Word> wordList = dictionary.getWordList();

            // Ghi từng từ vào tệp
            for (Word word : wordList) {
                fileWriter.write(word.getWordTarget() + "  " + word.getWordExplain() + "\n");
            }

            // Đóng FileWriter để hoàn thành quá trình ghi dữ liệu
            fileWriter.close();

            // Hiển thị thông báo thành công
            showSuccessAlert("Xuất dữ liệu từ điển thành công.");

        } catch (IOException e) {
            e.printStackTrace();
            // Hiển thị thông báo lỗi nếu có vấn đề
            showErrorAlert("Lỗi khi xuất dữ liệu từ điển.");
        }
    }


    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showGameTab() {
        Tab gameTab = new Tab("Trò Chơi");
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(15));

        Button startGameButton = new Button("Bắt đầu trò chơi");
        TextArea gameTextArea = new TextArea();
        TextField userInputField = new TextField();
        userInputField.setPromptText("Nhập từ mới");

        startGameButton.setOnAction(e -> {
            gameTextArea.clear();
            dictionaryManagement.playWordChainGame(userInputField, gameTextArea);
        });

        vbox.getChildren().addAll(startGameButton, gameTextArea, userInputField);
        gameTab.setContent(vbox);

        // Add the new tab to the TabPane
        tabPane.getTabs().add(gameTab);
        // Select the newly added tab
        tabPane.getSelectionModel().select(gameTab);
    }

    private boolean showLoginDialog() {
        // Tạo một dialog đăng nhập
        Dialog<Pair<String, String>> loginDialog = new Dialog<>();
        loginDialog.setTitle("Đăng Nhập");
        loginDialog.setHeaderText("Vui lòng đăng nhập để sử dụng từ điển\n'admin'\n'11111111'.");

        // Thiết lập nút đăng nhập và hủy
        ButtonType loginButtonType = new ButtonType("Đăng Nhập", ButtonBar.ButtonData.OK_DONE);
        loginDialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Tạo một lưới để đặt các trường nhập liệu
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Thêm các trường nhập liệu vào lưới
        TextField username = new TextField();
        username.setPromptText("Tên đăng nhập");
        PasswordField password = new PasswordField();
        password.setPromptText("Mật khẩu");

        grid.add(new Label("Tên đăng nhập:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Mật khẩu:"), 0, 1);
        grid.add(password, 1, 1);

        // Kích thước của dialog
        loginDialog.getDialogPane().setContent(grid);

        // Disable nút đăng nhập khi trường đăng nhập rỗng
        Node loginButton = loginDialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Kiểm tra nếu có dữ liệu ở các trường đăng nhập
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() || password.getText().trim().isEmpty());
        });

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() || username.getText().trim().isEmpty());
        });

        // Xử lý kết quả khi nút đăng nhập được nhấn
        loginDialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        // Hiển thị dialog và xử lý kết quả
        Optional<Pair<String, String>> result = loginDialog.showAndWait();
        return result.isPresent() && isValidCredentials(result.get().getKey(), result.get().getValue());
    }

    // Phương thức kiểm tra tài khoản và mật khẩu
    private boolean isValidCredentials(String username, String password) {
        return "admin".equals(username) && "11111111".equals(password);
    }


}
