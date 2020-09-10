package com.proyecto_tienda.pdf;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.proyecto_tienda.model.CabeceraPedido;
import com.proyecto_tienda.model.DetallePedido;



public class FacturaWriter {
	// logger
	private static Logger log = LogManager.getLogger(FacturaWriter.class);

	public File escribirFactura(CabeceraPedido cp) {

		try {

			PdfWriter writer = null;
			Document documento = new Document(PageSize.A4, 20, 20, 70, 50);

			ClassLoader cl = this.getClass().getClassLoader();

			writer = PdfWriter.getInstance(documento, new FileOutputStream(
					String.format("%s/factura-clientes%s.pdf", cl.getResource(".").getFile(), cp.getId())));

			writer.setPageEvent(new PdfHeaderFooter(cp));

			documento.open();

			PdfPTable tabla = new PdfPTable(2);

			Phrase textoCabecera1 = new Phrase("Producto");
			PdfPCell cabecera1 = new PdfPCell(textoCabecera1);
			cabecera1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cabecera1.setBorderWidth(1);

			Phrase textoCabecera2 = new Phrase("Precio");
			PdfPCell cabecera2 = new PdfPCell(textoCabecera2);
			cabecera2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cabecera2.setBorderWidth(1);

			tabla.addCell(cabecera1);
			tabla.addCell(cabecera2);

			for (DetallePedido dp : cp.getDetallePedidos()) {

				tabla.addCell(dp.getProducto().getDescripcion());
				tabla.addCell((dp.getProducto().getPrecioUnitarioSinIva()
						+ ((dp.getProducto().getPrecioUnitarioSinIva() * 21) / 100)) + " €");

			}

			tabla.addCell(" ");
			tabla.addCell(" ");
			tabla.addCell("Total: ");
			tabla.addCell(cp.getImporteTotal() + " €");

			documento.add(tabla);

			documento.close();
			writer.close();

			return new File(
					String.format("%s/factura-clientes%s.pdf", cl.getResource(".").getFile(), cp.getId()));

		} catch (Exception e) {

			log.error(String.format("Error al generar la facuta del pedido %d", cp.getId()));

			e.printStackTrace();

		}

		return null;

	}
}
