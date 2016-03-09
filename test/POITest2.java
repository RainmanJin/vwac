import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class POITest2{
  static HSSFWorkbook workbook;

  public static void main(String[] args){
    workbook = new HSSFWorkbook();

    HSSFSheet sheet = workbook.createSheet();

    HSSFRow row[] = new HSSFRow[12];
    for (int i = 0; i < 12 ; i++){
      row[i] = sheet.createRow(i);
    }

    HSSFCell cell[][] = new HSSFCell[12][4];
    for (int i = 0; i < 12; i++){
      for (int j = 0; j < 4 ; j++){
        cell[i][j] = row[i].createCell((short)j);
      }
    }

    setStyle(cell[0][0], "AQUA", HSSFColor.AQUA.index);
    setStyle(cell[0][1], "BLACK", HSSFColor.BLACK.index);
    setStyle(cell[0][2], "BLUE", HSSFColor.BLUE.index);
    setStyle(cell[0][3], "BLUE_GREY", HSSFColor.BLUE_GREY.index);
    setStyle(cell[1][0], "BRIGHT_GREEN",
                              HSSFColor.BRIGHT_GREEN.index);
    setStyle(cell[1][1], "BROWN", HSSFColor.BROWN.index);
    setStyle(cell[1][2], "CORAL", HSSFColor.CORAL.index);
    setStyle(cell[1][3], "CORNFLOWER_BLUE",
                              HSSFColor.CORNFLOWER_BLUE.index);
    setStyle(cell[2][0], "DARK_BLUE", HSSFColor.DARK_BLUE.index);
    setStyle(cell[2][1], "DARK_GREEN", HSSFColor.DARK_GREEN.index);
    setStyle(cell[2][2], "DARK_RED", HSSFColor.DARK_RED.index);
    setStyle(cell[2][3], "DARK_TEAL", HSSFColor.DARK_TEAL.index);
    setStyle(cell[3][0], "DARK_YELLOW",
                              HSSFColor.DARK_YELLOW.index);
    setStyle(cell[3][1], "GOLD", HSSFColor.GOLD.index);
    setStyle(cell[3][2], "GREEN", HSSFColor.GREEN.index);
    setStyle(cell[3][3], "GREY_25_PERCENT",
                              HSSFColor.GREY_25_PERCENT.index);
    setStyle(cell[4][0], "GREY_40_PERCENT",
                              HSSFColor.GREY_40_PERCENT.index);
    setStyle(cell[4][1], "GREY_50_PERCENT",
                              HSSFColor.GREY_50_PERCENT.index);
    setStyle(cell[4][2], "GREY_80_PERCENT",
                              HSSFColor.GREY_80_PERCENT.index);
    setStyle(cell[4][3], "INDIGO", HSSFColor.INDIGO.index);
    setStyle(cell[5][0], "LAVENDER", HSSFColor.LAVENDER.index);
    setStyle(cell[5][1], "LEMON_CHIFFON",
                              HSSFColor.LEMON_CHIFFON.index);
    setStyle(cell[5][2], "LIGHT_BLUE",
                              HSSFColor.LIGHT_BLUE.index);
    setStyle(cell[5][3], "LIGHT_CORNFLOWER_BLUE",
                          HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
    setStyle(cell[6][0], "LIGHT_GREEN",
                              HSSFColor.LIGHT_GREEN.index);
    setStyle(cell[6][1], "LIGHT_ORANGE",
                              HSSFColor.LIGHT_ORANGE.index);
    setStyle(cell[6][2], "LIGHT_TURQUOISE",
                              HSSFColor.LIGHT_TURQUOISE.index);
    setStyle(cell[6][3], "LIGHT_YELLOW",
                              HSSFColor.LIGHT_YELLOW.index);
    setStyle(cell[7][0], "LIME", HSSFColor.LIME.index);
    setStyle(cell[7][1], "MAROON", HSSFColor.MAROON.index);
    setStyle(cell[7][2], "OLIVE_GREEN",
                              HSSFColor.OLIVE_GREEN.index);
    setStyle(cell[7][3], "ORANGE", HSSFColor.ORANGE.index);
    setStyle(cell[8][0], "ORCHID", HSSFColor.ORCHID.index);
    setStyle(cell[8][1], "PALE_BLUE", HSSFColor.PALE_BLUE.index);
    setStyle(cell[8][2], "PINK", HSSFColor.PINK.index);
    setStyle(cell[8][3], "PLUM", HSSFColor.PLUM.index);
    setStyle(cell[9][0], "RED", HSSFColor.RED.index);
    setStyle(cell[9][1], "ROSE", HSSFColor.ROSE.index);
    setStyle(cell[9][2], "ROYAL_BLUE",
                              HSSFColor.ROYAL_BLUE.index);
    setStyle(cell[9][3], "SEA_GREEN", HSSFColor.SEA_GREEN.index);
    setStyle(cell[10][0], "SKY_BLUE", HSSFColor.SKY_BLUE.index);
    setStyle(cell[10][1], "TAN", HSSFColor.TAN.index);
    setStyle(cell[10][2], "TEAL", HSSFColor.TEAL.index);
    setStyle(cell[10][3], "TURQUOISE",
                              HSSFColor.TURQUOISE.index);
    setStyle(cell[11][0], "VIOLET", HSSFColor.VIOLET.index);
    setStyle(cell[11][1], "WHITE", HSSFColor.WHITE.index);
    setStyle(cell[11][2], "YELLOW", HSSFColor.YELLOW.index);

    FileOutputStream out = null;
    try{
      out = new FileOutputStream("/tmp/sample.xls");
      workbook.write(out);
    }catch(IOException e){
      System.out.println(e.toString());
    }finally{
      try {
        out.close();
      }catch(IOException e){
        System.out.println(e.toString());
      }
    }
  }

  public static void setStyle(HSSFCell cell, String col, short fg){
    HSSFCellStyle style = workbook.createCellStyle();
    style.setFillForegroundColor(fg);
    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    cell.setCellStyle(style);

    cell.setCellValue(col);
  }
}
