package com.nf147.ssm;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;

public class ExcelBean implements  java.io.Serializable{

        private String headTextName; //列头（标题）名

        private String propertyName; //对应字段名

        private Integer cols; //合并单元格数

        private XSSFCellStyle cellStyle;

        public ExcelBean(){

        }

        public ExcelBean(String headTextName, String propertyName){

            this.headTextName = headTextName;

            this.propertyName = propertyName;

        }

        public ExcelBean(String headTextName, String propertyName, Integer cols) {

            super();

            this.headTextName = headTextName;

            this.propertyName = propertyName;

            this.cols = cols;

        }

        /* 省略了get和set方法 */

    }

