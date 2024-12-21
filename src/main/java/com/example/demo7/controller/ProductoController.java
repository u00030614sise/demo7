/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo7.controller;

import com.example.demo7.model.Producto;
import com.example.demo7.service.ProductoService;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author leoru
 */
@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService personaService) {
        this.service = personaService;
    }

    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", this.service.listarTodas());
        return "productos";
    }

    @GetMapping("/reporte/pdf")
    public void generarPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=productos_reporte.pdf");

        PdfWriter write = new PdfWriter(response.getOutputStream());
        Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(write));

        document.add(new Paragraph("Reporte de productos").setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER));

        Table table = new Table(4);
        table.setWidth(UnitValue.createPercentValue(100))
                .setMarginTop(20)
                .setBorder(new SolidBorder(ColorConstants.BLACK, 1));
        table.addHeaderCell(new Paragraph("ID").setBold());
        table.addHeaderCell(new Paragraph("Nombre").setBold());
        table.addHeaderCell(new Paragraph("Descripción").setBold());
        table.addHeaderCell(new Paragraph("Precio").setBold());

        List<Producto> productos = this.service.listarTodas();
        for (Producto persona : productos) {
            table.addCell(persona.getId().toString());
            table.addCell(persona.getNombre());
            table.addCell(persona.getDescripcion());
            table.addCell(persona.getPrecio().toString());
        }

        document.add(table);
        document.close();
    }

    @GetMapping("/reporte/excel")
    public void generarExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=productos_reporte.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Productos");

        Row headerRow = sheet.createRow(0);
        String[] columnHeaders = {"ID", "Nombre", "Descripcion", "Precio"};
        for (int i = 0; i < columnHeaders.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnHeaders[i]);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            cell.setCellStyle(style);
        }

        List<Producto> productos = this.service.listarTodas();
        int rowIndex = 1;
        for (Producto producto : productos) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(producto.getId());
            row.createCell(1).setCellValue(producto.getNombre());
            row.createCell(2).setCellValue(producto.getDescripcion());
            row.createCell(3).setCellValue(producto.getPrecio());
        }

        workbook.write(response.getOutputStream());
        workbook.close();

    }

}
