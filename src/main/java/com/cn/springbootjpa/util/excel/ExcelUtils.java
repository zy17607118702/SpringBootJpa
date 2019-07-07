
package com.cn.springbootjpa.util.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * EXCEL表格的读取,创建工具之JXL版<br>
 * 注：本版本只适用于EXCEL2003及以前版本，EXCEL2007及以后版本参看ExcelUtils之POI版
 * 
 * @author danne
 */
public class ExcelUtils {

	/**
	 * 转换cell的值
	 * 
	 * @param cellFrom
	 *            源cell
	 * @return String 值
	 * @author chaxin A184
	 * @serialData 2013-2-19
	 */
	private static String exchagneValue(Cell cellFrom) {
		if (cellFrom != null) {
			return cellFrom.getStringCellValue();
		}
		return null;
	}

	/**
	 * 添加边框
	 * 
	 * @param sheet
	 *            页
	 * @param startRow
	 *            起始行号
	 * @param lastRow
	 *            截止行号
	 * @param endColumn
	 *            结束咧号
	 * @param style
	 *            样式
	 */
	public static void addBorder(Sheet sheet, int startRow, int lastRow, int endColumn, CellStyle style) {
		for (int i = startRow; i <= lastRow; i++) {
			if (sheet.getRow(i) == null) {
				sheet.createRow(i);
				sheet.getRow(i).setHeight((short) 350);
			}
			for (int j = 0; j <= endColumn; j++) {
				if (null == sheet.getRow(i).getCell(j)) {
					sheet.getRow(i).createCell(j);
				}
				sheet.getRow(i).getCell(j).setCellStyle(style);
			}
		}
	}

	/**
	 * 添加单元格值
	 * 
	 * @param sheet
	 *            页
	 * @param row
	 *            行号
	 * @param colomn
	 *            列号
	 * @param value
	 *            值
	 */
	@SuppressWarnings("deprecation")
	public static void addCellDoubleValue(Sheet sheet, int row, int colomn, Double value) {
		if (sheet.getRow(row) == null) {
			sheet.createRow(row);
		}
		if (sheet.getRow(row).getCell(colomn) == null) {
			sheet.getRow(row).createCell(colomn);
		}
		if (null != value) {
			sheet.getRow(row).getCell(colomn).setCellType(Cell.CELL_TYPE_NUMERIC);
			sheet.getRow(row).getCell(colomn).setCellValue(value);
		}
	}

	/**
	 * 添加单元格值
	 * 
	 * @param sheet
	 *            页
	 * @param row
	 *            行号
	 * @param colomn
	 *            列号
	 * @param value
	 *            值
	 * @param style
	 *            样式
	 * @param showZero
	 *            是否显示为零
	 */
	@SuppressWarnings("deprecation")
	public static void addCellDoubleValue(Sheet sheet, int row, int colomn, String value, CellStyle style,
			boolean showZero) {
		if (sheet.getRow(row) == null) {
			sheet.createRow(row);
		}
		if (sheet.getRow(row).getCell(colomn) == null) {
			sheet.getRow(row).createCell(colomn);
		}
		Double doubleValue = null;
		if (!org.apache.commons.lang3.StringUtils.isBlank(value) && !"null".equals(value) && !"0".equals(value)) {
			doubleValue = Double.valueOf(value);
		}
		if (showZero && doubleValue == null) {
			doubleValue = 0d;
		}
		if (null != doubleValue) {
			sheet.getRow(row).getCell(colomn).setCellType(Cell.CELL_TYPE_NUMERIC);
			sheet.getRow(row).getCell(colomn).setCellValue(doubleValue);
		}
		if (null != style) {
			sheet.getRow(row).getCell(colomn).setCellStyle(style);
		}
	}

	/**
	 * 添加单元格值
	 * 
	 * @param sheet
	 *            页
	 * @param row
	 *            行号
	 * @param colomn
	 *            列号
	 * @param value
	 *            值
	 * @param style
	 *            样式
	 */
	public static void addCellLongValue(Sheet sheet, int row, int colomn, String value, CellStyle style) {
		if (sheet.getRow(row) == null) {
			sheet.createRow(row);
		}
		if (sheet.getRow(row).getCell(colomn) == null) {
			sheet.getRow(row).createCell(colomn);
		}
		Long longValue = null;
		if (!org.apache.commons.lang3.StringUtils.isBlank(value) && !"null".equals(value)) {
			longValue = Long.valueOf(value).longValue();
		}
		if (null != longValue) {
			sheet.getRow(row).getCell(colomn).setCellValue(longValue);
		}
		if (null != style) {
			sheet.getRow(row).getCell(colomn).setCellStyle(style);
		}
	}

	/**
	 * 添加单元格值
	 * 
	 * @param sheet
	 *            页
	 * @param row
	 *            行号
	 * @param colomn
	 *            列号
	 * @param value
	 *            值
	 * @param style
	 *            样式
	 * @param showZero
	 *            是否显示为零
	 */
	public static void addCellLongValue(Sheet sheet, int row, int colomn, String value, CellStyle style,
			boolean showZero) {
		if (sheet.getRow(row) == null) {
			sheet.createRow(row);
		}
		if (sheet.getRow(row).getCell(colomn) == null) {
			sheet.getRow(row).createCell(colomn);
		}
		Long longValue = null;
		if (!org.apache.commons.lang3.StringUtils.isBlank(value) && !"null".equals(value)) {
			longValue = Long.valueOf(value).longValue();
		}
		if (null != longValue) {
			if (showZero || longValue.longValue() != 0) {
				sheet.getRow(row).getCell(colomn).setCellValue(longValue);
			} else {
				sheet.getRow(row).getCell(colomn).setCellValue("");
			}
		}

		if (null != style) {
			sheet.getRow(row).getCell(colomn).setCellStyle(style);
		}
	}

	/**
	 * 添加单元格值
	 * 
	 * @param sheet
	 *            页
	 * @param row
	 *            行号
	 * @param colomn
	 *            列号
	 * @param value
	 *            值
	 */
	public static void addCellStringValue(Sheet sheet, int row, int colomn, String value) {
		if (sheet.getRow(row) == null) {
			sheet.createRow(row);
		}
		if (sheet.getRow(row).getCell(colomn) == null) {
			sheet.getRow(row).createCell(colomn);
		}
		if (null != value) {
			sheet.getRow(row).getCell(colomn).setCellValue(value);
		}
	}

	/**
	 * 设定列宽度
	 * 
	 * @param sheet
	 *            页
	 * @param columnWidthes
	 *            列宽数组
	 */
	public static void addColumnWidth(Sheet sheet, Integer[] columnWidthes) {
		for (int i = 0; i < columnWidthes.length; i++) {
			if (null != columnWidthes[i]) {
				sheet.setColumnWidth(i, columnWidthes[i]);
			}
		}
	}

	/**
	 * 添加函数
	 * 
	 * @param sheet
	 *            页
	 * @param row
	 *            行号
	 * @param colomn
	 *            列号
	 * @param functionStr
	 *            函数
	 * @param style
	 *            样式
	 */
	@SuppressWarnings("deprecation")
	public static void addFunction(Sheet sheet, int row, int colomn, String functionStr, CellStyle style) {
		if (org.apache.commons.lang3.StringUtils.isBlank(functionStr)) {
			return;
		}
		if (sheet.getRow(row) == null) {
			sheet.createRow(row);
		}
		if (sheet.getRow(row).getCell(colomn) == null) {
			sheet.getRow(row).createCell(colomn);
		}
		if (org.apache.commons.lang3.StringUtils.isNotBlank(functionStr)) {
			sheet.getRow(row).getCell(colomn).setCellType(Cell.CELL_TYPE_FORMULA);
			sheet.getRow(row).getCell(colomn).setCellFormula(functionStr);
		}
		if (null != style) {
			sheet.getRow(row).getCell(colomn).setCellStyle(style);
		}
	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 *            页
	 * @param firstRow
	 *            起始行号
	 * @param lastRow
	 *            截止行号
	 * @param firstCol
	 *            起始列号
	 * @param lastCol
	 *            截止列号上
	 */
	public static void addMergedCellRange(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
		CellRangeAddress range = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
		sheet.addMergedRegion(range);
	}

	/**
	 * 同一页内COPY行列
	 * 
	 * @param sheet
	 *            需要COPY行的SHEET页
	 * @param sourceRow
	 *            源行
	 * @param lastCellNum
	 *            需要拷贝的列数
	 * @author lvbingbing
	 * @serialData 2013-3-19
	 */
	@SuppressWarnings("deprecation")
	public static void copyRow(Sheet sheet, int sourceRow, int lastCellNum) {
		// 初期化
		Row rowFrom = null;
		Row rowTo = null;
		boolean control = true;
		CellStyle cs = null;
		Cell cellFrom = null;
		Cell cellTo = null;
		rowFrom = sheet.getRow(sourceRow);
		rowTo = sheet.createRow(sourceRow + 1);
		rowTo.setHeight(rowFrom.getHeight());

		for (int intCol = 0; intCol < lastCellNum; intCol++) {

			cellFrom = rowFrom.getCell(intCol);
			cellTo = rowTo.createCell(intCol);
			if (null == cellFrom) {
				continue;
			}
			if (control) {
				cs = cellFrom.getCellStyle();
				control = false;
			}
			if (intCol == lastCellNum - 1) {
				cellTo.setCellStyle(cs);
			} else {
				cellTo.setCellStyle(cellFrom.getCellStyle());
			}
			cellTo.setCellType(cellFrom.getCellType());
			if (cellFrom.getCellType() == 1) {
				if (null != cellFrom.getStringCellValue() && !"".equals(cellFrom.getStringCellValue().trim())) {
					cellTo.setCellValue(cellFrom.getStringCellValue());
				}
			} else if (cellFrom.getCellType() == 0) {
				cellTo.setCellValue(cellFrom.getNumericCellValue());
			}
		}
	}

	/**
	 * 复制sheet页 （Excel2003）
	 * 
	 * @param sheetFrom
	 *            源sheet页
	 * @param sheetTo
	 *            目标sheet页
	 * @return Sheet 复制后的sheet页
	 */
	@SuppressWarnings("deprecation")
	public static Sheet copySheet2003(Sheet sheetFrom, Sheet sheetTo) {
		// 初期化
		CellRangeAddress region = null;
		Row rowFrom = null;
		Row rowTo = null;
		Cell cellFrom = null;
		Cell cellTo = null;
		for (int i = 0; i < sheetFrom.getNumMergedRegions(); i++) {
			region = sheetFrom.getMergedRegion(i);
			if ((region.getFirstColumn() >= sheetFrom.getFirstRowNum())
					&& (region.getLastRow() <= sheetFrom.getLastRowNum())) {
				sheetTo.addMergedRegion(region);
			}
		}

		for (int intRow = sheetFrom.getFirstRowNum(); intRow <= sheetFrom.getLastRowNum(); intRow++) {
			rowFrom = sheetFrom.getRow(intRow);
			rowTo = sheetTo.createRow(intRow);
			if (null == rowFrom) {
				continue;
			}
			rowTo.setHeight(rowFrom.getHeight());

			for (int intCol = 0; intCol < rowFrom.getLastCellNum(); intCol++) {
				if (sheetFrom.getColumnStyle(intCol) != null) {
					sheetTo.setDefaultColumnStyle(intCol, sheetFrom.getColumnStyle(intCol));
				}
				sheetTo.setColumnWidth(intCol, sheetFrom.getColumnWidth(intCol));
				cellFrom = rowFrom.getCell(intCol);
				cellTo = rowTo.createCell(intCol);
				if (null == cellFrom) {
					continue;
				}
				cellTo.setCellStyle(cellFrom.getCellStyle());
				cellTo.setCellType(cellFrom.getCellType());
				if (null != cellFrom.getStringCellValue() && !"".equals(cellFrom.getStringCellValue().trim())) {
					cellTo.setCellValue(cellFrom.getStringCellValue());
				}
			}
		}
		sheetTo.setDisplayGridlines(false);
		return sheetTo;
	}

	/**
	 * 复制sheet页（Excel2007）
	 * 
	 * @param sheetFrom
	 *            源sheet页
	 * @param sheetTo
	 *            目标sheet页
	 * @return Sheet 复制后的sheet页
	 */
	@SuppressWarnings("deprecation")
	public static Sheet copySheet2007(Sheet sheetFrom, Sheet sheetTo) {
		// 初期化
		CellRangeAddress region = null;
		Row rowFrom = null;
		Row rowTo = null;
		Cell cellFrom = null;
		Cell cellTo = null;

		for (int i = 0; i < sheetFrom.getNumMergedRegions(); i++) {
			region = sheetFrom.getMergedRegion(i);
			if ((region.getFirstColumn() >= sheetFrom.getFirstRowNum())
					&& (region.getLastRow() <= sheetFrom.getLastRowNum())) {
				sheetTo.addMergedRegion(region);
			}
		}

		for (int intRow = sheetFrom.getFirstRowNum(); intRow < sheetFrom.getLastRowNum(); intRow++) {
			rowFrom = sheetFrom.getRow(intRow);
			rowTo = sheetTo.createRow(intRow);
			if (null == rowFrom) {
				continue;
			}
			rowTo.setHeight(rowFrom.getHeight());
			for (int intCol = 0; intCol < rowFrom.getLastCellNum(); intCol++) {
				sheetTo.setDefaultColumnStyle(intCol, sheetFrom.getColumnStyle(intCol));
				sheetTo.setColumnWidth(intCol, sheetFrom.getColumnWidth(intCol));
				cellFrom = rowFrom.getCell(intCol);
				cellTo = rowTo.createCell(intCol);
				if (null == cellFrom) {
					continue;
				}
				cellTo.setCellStyle(cellFrom.getCellStyle());
				cellTo.setCellType(cellFrom.getCellType());

				if (null != exchagneValue(cellFrom) && !"".equals(exchagneValue(cellFrom))) {
					cellTo.setCellValue(exchagneValue(cellFrom));
				}
			}
		}

		sheetTo.setDisplayGridlines(false);
		return sheetTo;
	}

	/**
	 * 获取列名
	 * 
	 * @param columnIndex
	 *            列索引号
	 * @return String 列名
	 */
	public static String getColumnName(int columnIndex) {
		String str = "";
		if (columnIndex < 26) {
			str = String.valueOf((char) (columnIndex + 65));
		} else {
			int first = columnIndex / 26;
			int second = columnIndex % 26;
			str = "" + (char) (first - 1 + 65) + (char) (second + 65);
		}
		return str;
	}

	/**
	 * 批量插入行
	 * 
	 * @param sheet
	 *            页
	 * @param startRow
	 *            起始行号
	 * @param rows
	 *            行数
	 */
	@SuppressWarnings("deprecation")
	public static void insertRows(Sheet sheet, int startRow, int rows) {
		sheet.shiftRows(startRow, sheet.getLastRowNum(), rows, true, false);

		for (int i = 0; i < rows; i++) {
			Row sourceRow = null;// 原始位置
			Row targetRow = null;// 移动后位置
			Cell sourceCell = null;
			Cell targetCell = null;
			sourceRow = sheet.createRow(startRow);
			targetRow = sheet.getRow(startRow + rows);
			sourceRow.setHeight(targetRow.getHeight());

			for (int m = targetRow.getFirstCellNum(); m < targetRow.getPhysicalNumberOfCells(); m++) {
				sourceCell = sourceRow.createCell(m);
				targetCell = targetRow.getCell(m);
				if (targetCell == null) {
					continue;
				}
				sourceCell.setCellStyle(targetCell.getCellStyle());
				sourceCell.setCellType(targetCell.getCellType());
			}
			startRow++;
		}

	}

	/**
	 * 
	 * @param cell
	 *            数据
	 * @return String单元格数据
	 */
	@SuppressWarnings("deprecation")
	public static String getString(HSSFCell cell) {

		if (cell == null) {
			return null;
		}
		String value = null;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			/* System.out.println("CELL_TYPE_STRING"); */
			value = cell.getRichStringCellValue().getString();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			/* System.out.println("CELL_TYPE_STRING"); */
			double val = cell.getNumericCellValue();
			value = String.valueOf((int) val);
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			/* System.out.println("CELL_TYPE_FORMULA"); */
			value = cell.getCellFormula();
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			/* System.out.println("CELL_TYPE_BLANK"); */
			value = null;
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			/* System.out.println("CELL_TYPE_BOOLEAN"); */
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			System.out.println("CELL_TYPE_ERROR");
			value = String.valueOf(cell.getErrorCellValue());
			break;
		default:
			/* System.out.println("CELL_TYPE:" + cell.getCellType()); */
			value = cell.getStringCellValue();
			break;
		}
		return value;
	}

	/**
	 * 
	 * @param row
	 *            行数
	 * @param i
	 *            格数
	 * @return String单元格数据
	 */
	public static double getDouble(HSSFRow row, int i) {
		HSSFCell cell = row.getCell(i);
		return getDouble(cell);
	}

	/**
	 * 
	 * 转换Excel数据格式
	 * 
	 * @param cell
	 *            Excel数据
	 * @return Excel数据
	 */
	@SuppressWarnings("deprecation")
	public static double getDouble(HSSFCell cell) {
		if (cell == null) {
			return 0d;
		}
		double value = 0;
		if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			value = cell.getNumericCellValue();
		} else {
			try {
				if (getString(cell) != null) {
					value = Double.parseDouble(getString(cell));
				}
			} catch (NumberFormatException e) {
				// e.printStackTrace();
			}
		}
		return value;
	}

}