/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package editor.save.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public interface IOManager {
      String CONFIG_FOLDER = ".config";
      String TOOLBAR_CONFIG_FILE = CONFIG_FOLDER + "/toolbar";
      String SAVE_DIRECTORY = "save";

      String EDITOR_TOKEN = "editor";
      String TOOLBAR_TOKEN = "toolbar";
      String SCENE_TOKEN = "scene";
      String GROUP_TOKEN = "group";
      String RECTANGLE_TOKEN = "rectangle";
      String POLYGON_TOKEN = "polygon";
      String COLOR_TOKEN = "color";
      String RED_TOKEN = "r";
      String GREEN_TOKEN = "g";
      String BLUE_TOKEN = "b";
      String OPACITY_TOKEN = "opacity";
      String ROTATION_TOKEN = "rotation";
      String POSITION_TOKEN = "position";
      String ROTATION_CENTER_TOKEN = "rotationCenter";
      String TRANSLATION_TOKEN = "translation";
      String X_TOKEN = "x";
      String Y_TOKEN = "y";
      String WIDTH_TOKEN = "width";
      String HEIGHT_TOKEN = "height";
      String BORDER_RADIUS_TOKEN = "borderRadius";
      String NB_SIDES_TOKEN = "nbSides";
      String SIDE_LENGTH = "sideLength";

      String getExtension();

      static void writeInFile(File file, String data, String extension) {
            if (file != null) {
                  if (file.getName().endsWith(extension)) {
                        System.out.println("save file : " + file.getName());
                        try {
                              FileWriter myWriter = new FileWriter(file);
                              myWriter.write(data);
                              myWriter.close();
                        } catch (IOException e) {
                              e.printStackTrace();
                        }
                  } else
                        throw new IOEditorException(file.getName() + " has no valid file-extension.");
            }
      }

      static String readFile(File file){
            if (file != null) {
                  System.out.println("open file : " + file.getName());
                  Scanner myReader;
                  try {
                        myReader = new Scanner(file);
                        StringBuilder sb = new StringBuilder();
                        while (myReader.hasNextLine()) {
                              sb.append(myReader.nextLine());
                        }
                        myReader.close();
                        return sb.toString();
                  } catch (FileNotFoundException e) {
                        e.printStackTrace();
                  }
            }
            return null;
      }
}
