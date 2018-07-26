package com.github.teocci.codesample.javafx.uisamples.features;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
import com.github.teocci.codesample.javafx.elements.SearchHighlightedTextCell;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListViewTextHighlighter extends Application {
    public static void main(String[] args) throws Exception { launch(args); }

    private static final String LOREM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent ullamcorper, tellus nec adipiscing luctus, diam urna dignissim justo, et pellentesque tortor turpis non leo. Praesent ut sagittis tellus, et adipiscing metus. Mauris lectus sem, gravida eget leo quis, pretium interdum justo. Cras elit lorem, euismod sed dolor in, vulputate hendrerit felis. Maecenas fermentum vulputate ligula vel sodales. Donec semper risus mattis, scelerisque tortor at, mattis orci. Nunc laoreet at risus nec placerat. Maecenas quis nunc est. Interdum et malesuada fames ac ante ipsum primis in faucibus.\n\nDuis tincidunt, enim eget adipiscing interdum, nulla justo mattis ante, eget hendrerit dolor nulla mattis turpis. Phasellus in enim leo. Donec bibendum nisi odio, eget interdum augue ornare elementum. Nullam eget ipsum nec risus dapibus feugiat. Curabitur gravida nisl eget aliquet ultricies. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc quis scelerisque mi. Fusce venenatis convallis purus eu suscipit. Nullam augue dolor, ultrices ornare elementum ut, lobortis vel erat. Pellentesque molestie massa non ante imperdiet, venenatis ultricies lorem egestas. Nam enim libero, posuere id nisi sit amet, tempus lobortis purus. Nam ut tortor et est lacinia semper vitae vel tortor. Donec rhoncus tellus dignissim volutpat luctus. Mauris sit amet nisi sem.\n\nMaecenas accumsan tincidunt lectus, id vestibulum dui eleifend in. Mauris ac orci lacus. Suspendisse nec nisl tellus. In tincidunt augue et ligula accumsan, a sollicitudin elit venenatis. Nulla in tempus ipsum, eu porttitor ipsum. Nam neque leo, commodo a porta fermentum, mollis vel dolor. Maecenas feugiat eros volutpat erat fringilla aliquam mollis at dui. Fusce vel velit est. Aliquam pretium elit id massa aliquam, ac egestas erat hendrerit.\n\nNunc ut nulla ut lectus bibendum molestie sit amet non sem. Praesent a est quis nulla tempor adipiscing in malesuada libero. Aliquam nec dolor est. Proin elementum lorem vitae nunc convallis, laoreet mattis nisl facilisis. Sed neque leo, aliquet ut ligula ac, placerat vehicula mi. Fusce varius eget enim quis laoreet. Phasellus a porta mauris. Vestibulum feugiat sed enim vel semper. Nullam porttitor in nibh at faucibus. Donec sed nunc vitae erat ornare porttitor. Donec sodales libero id porttitor gravida.\n\nPhasellus feugiat egestas lorem, nec interdum lacus imperdiet id. Sed elementum neque vitae quam faucibus vulputate. Ut risus nisl, tincidunt nec lacinia eu, ultricies at neque. Nulla facilisi. Quisque a odio eget erat pulvinar pellentesque. Suspendisse in odio non nisi tristique pellentesque nec non est. Vivamus scelerisque pulvinar suscipit. Fusce quis eleifend risus, quis sollicitudin libero.\n\nMaecenas facilisis odio urna, at egestas nulla pellentesque eget. Proin ac luctus urna, consequat luctus arcu. Etiam egestas nisl vel metus suscipit, consectetur facilisis nulla varius. Fusce augue lorem, dapibus id mauris vel, feugiat dapibus tellus. Aliquam posuere elit sit amet felis interdum, consequat consectetur nulla adipiscing. Donec viverra pellentesque iaculis. Mauris arcu neque, laoreet a accumsan nec, dignissim id erat. Sed vehicula id nulla eu eleifend.\n\nDonec pretium quam ac rutrum feugiat. Praesent lobortis tellus non odio laoreet cursus. Vivamus porta orci venenatis, faucibus risus eget, aliquam orci. Donec odio ligula, consequat id ultrices sed, cursus auctor nibh. Suspendisse nec dui mauris. Integer interdum commodo nisi vel dictum. Integer a sem tristique, interdum nibh nec, consequat risus. Morbi volutpat nisi sapien, nec porttitor neque convallis in. Vestibulum eleifend, nisi ac malesuada aliquet, lacus elit venenatis dui, sit amet dapibus eros turpis eu mauris. Nunc non feugiat mauris. Pellentesque fermentum purus sed mauris dignissim varius. Proin risus tellus, blandit vel auctor ut, ultrices vitae massa. Duis pharetra odio eu eleifend vestibulum.\n\nNam risus nisl, auctor et mollis nec, commodo eu quam. Nunc nec fermentum dui, non sollicitudin urna. Praesent adipiscing elit magna, a venenatis diam dignissim et. Pellentesque imperdiet vestibulum lobortis. Curabitur ac risus nec dui mollis facilisis. Sed euismod iaculis nulla, sed faucibus neque consectetur sed. Etiam vel accumsan sem. Praesent dictum vestibulum auctor. Integer condimentum sollicitudin ullamcorper. Nulla lacinia ac tortor eu bibendum. Maecenas quis hendrerit augue, ac vulputate lorem. Vestibulum eget ipsum iaculis, sollicitudin turpis vel, interdum metus. Mauris id posuere orci.\n\nAliquam a cursus magna. Fusce luctus, nisi eu mattis accumsan, arcu ante fermentum ipsum, sed lobortis sapien velit non nisi. Proin at lectus imperdiet, suscipit eros sed, commodo tortor. Praesent eget ligula vitae tellus accumsan congue. Mauris ullamcorper risus vel dolor pellentesque, id volutpat nunc posuere. Fusce scelerisque sagittis purus, ut luctus mi eleifend ac. Proin pharetra commodo justo, quis ornare arcu tempor sed. Nullam blandit justo eu elementum eleifend. Nullam elit magna, scelerisque a sem quis, luctus eleifend augue. Ut ultricies tortor ligula, eget elementum lorem iaculis eget. Suspendisse eget libero mattis risus pulvinar sodales. Fusce ut sodales mauris. Aenean ac nibh sem. In non pretium dui. Nam vel mattis orci, ut condimentum sapien.\n\nNulla quis eros felis. Cras blandit accumsan ipsum, ac faucibus massa feugiat sagittis. Duis vitae diam dui. In pretium dapibus urna, a vulputate mi dictum nec. Sed id consequat augue. Integer ut nunc et neque ornare imperdiet vitae non erat. Curabitur non faucibus purus. Praesent velit ante, dictum vitae quam et, tempus ultricies mauris. Ut viverra ligula tellus, ac mattis leo fermentum eu. Praesent lobortis augue quis orci tristique, ac elementum enim aliquam. Ut ac eros consequat, vehicula nibh at, aliquet leo. Donec erat neque, sagittis a turpis ac, dictum consequat felis.\n\nVivamus a libero volutpat nunc molestie dignissim. Maecenas fermentum vulputate tempor. In laoreet eu purus ac commodo. Fusce velit eros, porttitor nec dui vitae, aliquet adipiscing lectus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. In molestie leo nibh, eget posuere mi accumsan eu. Sed venenatis elit nisi, id mattis dolor eleifend eget. Vivamus sit amet diam quis dolor lobortis tempor. Suspendisse faucibus imperdiet turpis in fringilla. Vestibulum commodo facilisis viverra. Fusce porta risus at leo dictum, a condimentum dolor faucibus. Aenean lobortis tellus purus, at adipiscing leo hendrerit vitae. Sed sed posuere nisi.\n\nUt sit amet dignissim elit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nulla elit nibh, malesuada non est venenatis, fringilla pharetra velit. Morbi in velit ut arcu lobortis malesuada. Integer et eleifend mauris, eget malesuada velit. Sed posuere vulputate egestas. Sed eleifend sollicitudin purus sit amet vehicula. Nulla odio diam, posuere non enim cursus, consequat cursus enim. Curabitur at ipsum felis. Donec consectetur nunc id urna commodo, at fermentum est blandit. Suspendisse potenti. Sed in semper tortor. Suspendisse in augue eu sapien vehicula malesuada eget nec mauris.\n\nNunc imperdiet ullamcorper metus, ac dapibus quam sodales viverra. Quisque commodo urna diam, ac rhoncus sapien euismod non. Fusce tincidunt diam dui, tempus bibendum purus fermentum in. Ut sed sapien sed nibh tempor aliquam. Pellentesque ullamcorper eros ac mauris interdum vehicula. Pellentesque et metus sit amet mauris blandit egestas eget sit amet ligula. Nunc quis risus semper, tempus nulla sed, viverra ipsum. Donec sed ante imperdiet, ornare quam eu, euismod urna. Morbi quis sollicitudin odio. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque id arcu gravida, adipiscing felis in, tristique orci. Morbi sit amet arcu quis sapien iaculis ultricies. Integer imperdiet auctor enim in elementum. Duis felis leo, congue vel dui nec, porttitor egestas urna.\n\nSuspendisse potenti. Praesent lectus purus, lacinia et porttitor sed, ultrices nec lectus. Cras sollicitudin nulla eget lectus pellentesque pulvinar. Etiam lobortis pulvinar libero adipiscing porttitor. Praesent id urna lectus. Vestibulum quis laoreet orci. Integer in augue et tortor tempus vestibulum in vitae orci. Curabitur eleifend leo quis augue commodo rhoncus. Aenean placerat metus in mattis hendrerit.\n\nProin auctor odio eu congue ultricies. Praesent ultricies, massa sit amet adipiscing tristique, est dui ornare risus, at congue arcu orci at libero. In hac habitasse platea dictumst. Duis ut lectus nibh. Vivamus ut metus id quam malesuada mattis nec viverra dolor. Suspendisse potenti. Nullam ut elit orci.\n\nIn laoreet eleifend ligula, sed dapibus ligula aliquam vitae. Sed mattis fermentum elit vitae placerat. Donec venenatis congue lacinia. Nam convallis vestibulum porttitor. Cras non nunc tellus. Nullam ut convallis orci. Sed sed malesuada urna, quis elementum dolor. Aliquam ullamcorper ornare nisi ut scelerisque. Sed cursus placerat mi ac tempor. Mauris ut mauris nec magna convallis pulvinar id ut lacus. Fusce convallis sollicitudin varius. Praesent molestie pellentesque diam auctor iaculis.\n\nPellentesque pharetra, tellus porta aliquam posuere, neque enim hendrerit turpis, at sagittis arcu mi sed tellus. Maecenas augue ante, porta a iaculis ut, aliquam et dui. Ut adipiscing tortor libero, id sodales enim posuere sed. Pellentesque tellus urna, mollis a bibendum ut, vehicula vel metus. Nunc sodales ante ut bibendum fringilla. Ut nunc mauris, pellentesque eget velit non, rhoncus porta diam. Curabitur eleifend nibh diam, nec tempus arcu facilisis vitae.\n\nNunc posuere nibh nisl, nec ornare ipsum tempor ac. Fusce congue laoreet condimentum. Vivamus gravida risus quam, sit amet mattis diam placerat eu. Quisque dolor lectus, suscipit tempor arcu vel, adipiscing fringilla dolor. Phasellus pulvinar nisi ante, at vehicula velit volutpat iaculis. Etiam ultricies sapien ut mauris condimentum euismod. Integer felis leo, lacinia id libero et, bibendum porttitor tortor. Curabitur adipiscing purus risus, at fringilla eros posuere in. Cras adipiscing velit est, nec commodo risus sollicitudin eget. Vivamus nec adipiscing felis, sit amet tristique nunc. Phasellus vel magna sodales, luctus odio non, ornare elit. Pellentesque ut purus dui. Fusce in mattis purus, a egestas ipsum. Suspendisse fermentum neque vitae tristique adipiscing.\n\nVivamus viverra placerat scelerisque. Phasellus pretium urna id turpis adipiscing malesuada. Ut tincidunt congue neque eget tristique. Sed hendrerit dolor vel lorem gravida, ac laoreet augue condimentum. Nulla facilisi. Donec odio tellus, lacinia sed lobortis eu, aliquet sit amet elit. Ut id aliquam erat. Donec tempor eget velit non pellentesque. Morbi vitae sagittis tortor, vel bibendum lorem.\n\nMorbi consectetur neque non dolor malesuada, non ultrices velit hendrerit. Etiam porttitor erat venenatis tellus faucibus ultricies. In dictum nec quam adipiscing vulputate. Praesent fringilla, eros viverra lacinia aliquam, diam arcu egestas felis, ut consequat neque elit in erat. Etiam placerat tincidunt risus quis varius. Aenean feugiat facilisis erat at pretium. Donec id dignissim libero. Donec mauris sapien, pretium id eleifend non, pellentesque sit amet arcu. Phasellus molestie faucibus magna at gravida. Nam iaculis accumsan quam. Nunc sagittis pharetra nunc quis condimentum. Fusce sed dictum arcu. Phasellus gravida mattis turpis nec vulputate. Nunc eleifend aliquet diam, varius tristique diam pretium ut.";
    private static final int    WRAPPING_WIDTH = 80;
    private static final int    PREF_TEXT_DISPLAY_WIDTH = 480;

    private final StringProperty searchText = new SimpleStringProperty("");

    @Override
    public void start(final Stage stage) throws Exception {
        VBox layout = new VBox(10);
        layout.getChildren().setAll(
                createSearchControls(),
                createTextDisplay()
        );

        layout.getStylesheets().add(getClass().getResource("/css/highlighter.css").toExternalForm());

        stage.setTitle("Text Highlighter");
        stage.setScene(new Scene(layout));
        stage.show();
    }

    private ListView<String> createTextDisplay() {
        ListView<String> textDisplay = new ListView<>(
                createLinesFromString(LOREM, WRAPPING_WIDTH)
        );
        VBox.setVgrow(textDisplay, Priority.ALWAYS);
        textDisplay.setPrefWidth(PREF_TEXT_DISPLAY_WIDTH);
        textDisplay.setCellFactory(stringListView -> new SearchHighlightedTextCell(searchText));
        return textDisplay;
    }

    private HBox createSearchControls() {
        final TextField searchField = new TextField();
        searchField.setPromptText("Enter Search Text");
        final Button searchButton = new Button("Search");
        searchButton.setDefaultButton(true);

        searchButton.setOnAction(actionEvent -> searchText.setValue(searchField.getText()));

        HBox controls = new HBox(10);
        controls.getChildren().setAll(
                searchField,
                searchButton
        );
        HBox.setHgrow(searchField, Priority.ALWAYS);
        return controls;
    }

    private ObservableList<String> createLinesFromString(String lorem, int wrappingWidth) {
        String[] parts = lorem.split("\\n");
        if (wrappingWidth <= 0) {
            return FXCollections.observableArrayList(parts);
        }

        ObservableList<String> lines = FXCollections.observableArrayList();
        for (String part: parts) {
            if (part.length() == 0) {
                lines.add(part);
                continue;
            }

            int offset = 0;
            String nextLine = getNextLine(part, offset, wrappingWidth);
            while (nextLine.length() > 0) {
                lines.add(nextLine);
                offset += nextLine.length();
                nextLine = getNextLine(part, offset, wrappingWidth);
            }
        }

        return lines;
    }

    private String getNextLine(String part, int offset, int wrappingWidth) {
        if ((part.length() - offset) <= wrappingWidth) {
            return part.substring(offset);
        }

        int idx = wrappingWidth;
        while (idx >= 0) {
            if (part.charAt(offset + idx) == ' ') {
                return part.substring(offset, offset + idx + 1);
            }
            idx--;
        }

        return part.substring(offset, Math.min(part.length(), offset + wrappingWidth));
    }
}
