package com.github.teocci.codesample.javafx.uisamples.style;

import com.github.teocci.codesample.javafx.models.Friend;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * JavaFX TableView row highlighting sample using table row apis and css.
 * This sample demonstrates highlighting rows in a tableview based upon the data values in the rows.
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

public class DebtCollectionTableWithRowHighlighting extends Application
{
    @Override
    public void start(final Stage stage) throws Exception
    {
        stage.setTitle("So called friends . . .");

        // create a table.
        TableView<Friend> table = new TableView(Friend.data);
        table.getColumns().addAll(makeStringColumn("Name", "name", 150), makeStringColumn("Owes Me", "owesMe", 300), makeBooleanColumn("Will Pay Up", "willPay", 150));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        stage.setScene(new Scene(table));
        stage.getScene().getStylesheets().add(getClass().getResource("/css/reaper.css").toExternalForm());
        stage.show();
    }

    private TableColumn<Friend, String> makeStringColumn(String columnName, String propertyName, int prefWidth)
    {
        TableColumn<Friend, String> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        column.setCellFactory(new Callback<TableColumn<Friend, String>, TableCell<Friend, String>>()
        {
            @Override
            public TableCell<Friend, String> call(TableColumn<Friend, String> soCalledFriendStringTableColumn)
            {
                return new TableCell<Friend, String>()
                {
                    @Override
                    public void updateItem(String item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                        }
                    }
                };
            }
        });
        column.setPrefWidth(prefWidth);

        return column;
    }

    private TableColumn<Friend, Boolean> makeBooleanColumn(String columnName, String propertyName, int prefWidth)
    {
        TableColumn<Friend, Boolean> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        column.setCellFactory(new Callback<TableColumn<Friend, Boolean>, TableCell<Friend, Boolean>>()
        {
            @Override
            public TableCell<Friend, Boolean> call(TableColumn<Friend, Boolean> soCalledFriendBooleanTableColumn)
            {
                return new TableCell<Friend, Boolean>()
                {
                    @Override
                    public void updateItem(final Boolean item, final boolean empty)
                    {
                        super.updateItem(item, empty);

                        // clear any custom styles
                        this.getStyleClass().remove("willPayCell");
                        this.getStyleClass().remove("wontPayCell");
                        this.getTableRow().getStyleClass().remove("willPayRow");
                        this.getTableRow().getStyleClass().remove("wontPayRow");

                        // update the item and set a custom style if necessary
                        if (item != null) {
                            setText(item.toString());
                            this.getStyleClass().add(item ? "willPayCell" : "wontPayCell");
                            this.getTableRow().getStyleClass().add(item ? "willPayRow" : "wontPayRow");
                        }
                    }
                };
            }
        });
        column.setPrefWidth(prefWidth);

        return column;
    }

    public static void main(String[] args) throws Exception { launch(args); }
}