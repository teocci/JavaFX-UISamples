package com.github.teocci.codesample.javafx.uisamples.features;

import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import static javafx.scene.SceneAntialiasing.BALANCED;

/**
 * Simplistic JavaFX 3D ModelViewer
 * <p>
 * Disclaimer:
 * texture found from: https://www.pexels.com/photo/abstract-ancient-antique-art-235985/
 * texture sourced from: pixabay.com
 * texture license: https://www.pexels.com/creative-commons-images/ => Creative Commons Zero
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */

public class InlineModelViewer extends Application
{
    private static final int VIEWPORT_SIZE = 800;

    private static final double MODEL_SCALE_FACTOR = 40;
    private static final double MODEL_X_OFFSET = 0;
    private static final double MODEL_Y_OFFSET = 0;
    private static final double MODEL_Z_OFFSET = VIEWPORT_SIZE / 2;

    //  private static final String TEXTURE_URL = "http://icons.iconarchive.com/icons/aha-soft/desktop-halloween/256/Skull-and-bones-icon.png"; // icon license linkware, backlink to http://www.aha-soft.com
    private static final String TEXTURE_URL = "https://images.pexels.com/photos/235985/pexels-photo-235985.jpeg";

    private Image texture;

    private PhongMaterial texturedMaterial = new PhongMaterial();

    private MeshView meshView = loadMeshView();

    private MeshView loadMeshView()
    {
//        float[] points = {
//                -5, 5, 0,
//                -5, -5, 0,
//                5, 5, 0,
//                5, -5, 0
//        };
//        float[] texCoords = {
//                1, 1,
//                1, 0,
//                0, 1,
//                0, 0
//        };
//        int[] faces = {
//                2, 2, 1, 1, 0, 0,
//                2, 2, 3, 3, 1, 1
//        };

        float[] points = {
                5, 5, 5,    //P3
                5, 5, -5,   //P7
                5, -5, 5,   //P1
                5, -5, -5,  //P5
                -5, 5, 5,   //P2
                -5, 5, -5,  //P6
                -5, -5, 5,  //P0
                -5, -5, -5, //P4
        };
        float[] texCoords = {
                0.25f, 0,       //T0
                0.5f, 0,        //T1
                0, 0.25f,       //T2
                0.25f, 0.25f,   //T3
                0.5f, 0.25f,    //T4
                0.75f, 0.25f,   //T5
                1, 0.25f,       //T6
                0, 0.5f,        //T7
                0.25f, 0.5f,    //T8
                0.5f, 0.5f,     //T9
                0.75f, 0.5f,    //T10
                1, 0.5f,        //T11
                0.25f, 0.75f,   //T12
                0.5f, 0.75f     //T13
        };
        int[] faces = {
                0, 10, 2, 5, 1, 9,
                2, 5, 3, 4, 1, 9,
                4, 7, 5, 8, 6, 2,
                6, 2, 5, 8, 7, 3,
                0, 13, 1, 9, 4, 12,
                4, 12, 1, 9, 5, 8,
                2, 1, 6, 0, 3, 4,
                3, 4, 6, 0, 7, 3,
                0, 10, 4, 11, 2, 5,
                2, 5, 4, 11, 6, 6,
                1, 9, 3, 4, 5, 8,
                5, 8, 3, 4, 7, 3
//                5, 1, 4, 0, 0, 3     //P5,T1 ,P4,T0  ,P0,T3
//                , 5, 1, 0, 3, 1, 4    //P5,T1 ,P0,T3  ,P1,T4
//                , 0, 3, 4, 2, 6, 7    //P0,T3 ,P4,T2  ,P6,T7
//                , 0, 3, 6, 7, 2, 8    //P0,T3 ,P6,T7  ,P2,T8
//                , 1, 4, 0, 3, 2, 8    //P1,T4 ,P0,T3  ,P2,T8
//                , 1, 4, 2, 8, 3, 9    //P1,T4 ,P2,T8  ,P3,T9
//                , 5, 5, 1, 4, 3, 9    //P5,T5 ,P1,T4  ,P3,T9
//                , 5, 5, 3, 9, 7, 10   //P5,T5 ,P3,T9  ,P7,T10
//                , 4, 6, 5, 5, 7, 10   //P4,T6 ,P5,T5  ,P7,T10
//                , 4, 6, 7, 10, 6, 11  //P4,T6 ,P7,T10 ,P6,T11
//                , 3, 9, 2, 8, 6, 12   //P3,T9 ,P2,T8  ,P6,T12
//                , 3, 9, 6, 12, 7, 13  //P3,T9 ,P6,T12 ,P7,T13
        };

        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().setAll(points);
        mesh.getTexCoords().setAll(texCoords);
        mesh.getFaces().setAll(faces);

        return new MeshView(mesh);
    }

    private Group buildScene()
    {
        meshView.setTranslateX(VIEWPORT_SIZE / 2 + MODEL_X_OFFSET);
        meshView.setTranslateY(VIEWPORT_SIZE / 2 * 9.0 / 16 + MODEL_Y_OFFSET);
        meshView.setTranslateZ(VIEWPORT_SIZE / 2 + MODEL_Z_OFFSET);
        meshView.setScaleX(MODEL_SCALE_FACTOR);
        meshView.setScaleY(MODEL_SCALE_FACTOR);
        meshView.setScaleZ(MODEL_SCALE_FACTOR);
        meshView.setMaterial(texturedMaterial);

        return new Group(meshView);
    }

    @Override
    public void start(Stage stage)
    {
        texture = new Image(TEXTURE_URL);
        texturedMaterial.setDiffuseMap(texture);
        texturedMaterial.setDiffuseColor(Color.RED);
        texturedMaterial.setSpecularColor(Color.WHITE);

        Group group = buildScene();

        RotateTransition rotate = rotate3dGroup(group);

        VBox layout = new VBox(
                createControls(rotate),
                createScene3D(group)
        );

        stage.setTitle("Model Viewer");

        Scene scene = new Scene(layout, Color.CORNSILK);
        stage.setScene(scene);
        stage.show();
    }

    private SubScene createScene3D(Group group)
    {
        SubScene scene3d = new SubScene(group, VIEWPORT_SIZE, VIEWPORT_SIZE * 9.0 / 16, true, BALANCED);
//        scene3d.setFill(Color.rgb(10, 10, 40));
        scene3d.setCamera(new PerspectiveCamera());
        return scene3d;
    }

    private VBox createControls(RotateTransition rotateTransition)
    {
        CheckBox cull = new CheckBox("Cull Back");
        meshView.cullFaceProperty().bind(
                Bindings.when(
                        cull.selectedProperty())
                        .then(CullFace.BACK)
                        .otherwise(CullFace.NONE)
        );
        CheckBox wireframe = new CheckBox("Wireframe");
        meshView.drawModeProperty().bind(
                Bindings.when(
                        wireframe.selectedProperty())
                        .then(DrawMode.LINE)
                        .otherwise(DrawMode.FILL)
        );

        CheckBox rotate = new CheckBox("Rotate");
        rotate.selectedProperty().addListener(observable -> {
            if (rotate.isSelected()) {
                rotateTransition.play();
            } else {
                rotateTransition.pause();
            }
        });

        CheckBox texture = new CheckBox("Texture");
        meshView.materialProperty().bind(Bindings
                .when(texture.selectedProperty())
                .then(texturedMaterial)
                .otherwise((PhongMaterial) null)
        );
        texture.setSelected(true);

        VBox controls = new VBox(10, rotate, texture, cull, wireframe);
        controls.setPadding(new Insets(10));
        return controls;
    }

    private RotateTransition rotate3dGroup(Group group)
    {
        RotateTransition rotate = new RotateTransition(Duration.seconds(10), group);
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setFromAngle(0);
        rotate.setToAngle(360);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setCycleCount(RotateTransition.INDEFINITE);

        return rotate;
    }

    public static void main(String[] args)
    {
        System.setProperty("prism.dirtyopts", "false");
        launch(args);
    }
}
