package com.proyecto_tienda.pdf;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.proyecto_tienda.model.CabeceraPedido;


public class PdfHeaderFooter extends PdfPageEventHelper {
	

	// logger
	private static Logger log = LogManager.getLogger(PdfHeaderFooter.class);

	// Template para el número total de páginas
	PdfTemplate total;

	private CabeceraPedido cp;

	public PdfHeaderFooter(CabeceraPedido cp) {

		this.cp = cp;

	}

	public void onStartPage(PdfWriter writer, Document document) {

		Phrase linea;
		Phrase imgCabecera;
		Phrase txtCabecera;
		Image imagen;

		PdfContentByte cb = writer.getDirectContent();

		try {

			ClassLoader cl = this.getClass().getClassLoader();

			imagen = Image.getInstance(String.format("%s/static/img/logo-pccomponentes.jpg", cl.getResource(".").getFile()));
			Chunk chunk = new Chunk(imagen, 0, -60);

			imgCabecera = new Phrase(chunk);
			ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, imgCabecera, document.rightMargin() + 90,
					document.top() + 60, 0);

//			SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
//			String fecha = formateador.format(cp.getFechaPedido());
//			txtFecha = new Phrase(fecha, new Font(FontFactory.getFont("Sans", 8, Font.NORMAL, BaseColor.BLACK)));
//			ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, txtFecha, (document.right() - document.left()),
//					document.top() + 30, 0);

			txtCabecera = new Phrase(String.format("Nº pedido: %d", cp.getId()));
			ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, txtCabecera, (document.right() - document.left()),
					document.top(), 0);

			linea = new Phrase();
			linea.add(new LineSeparator(1, new Float(2.8), BaseColor.BLACK, Element.ALIGN_LEFT, 0));
			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, linea, document.rightMargin(), document.top() - 5, 0);

			linea = new Phrase();
			linea.add(new LineSeparator(1, new Float(2.8), BaseColor.BLACK, Element.ALIGN_LEFT, 0));
			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, linea, document.rightMargin(), document.top() - 730, 0);

			document.add(new Paragraph(" "));

		} catch (Exception e) {

			log.error("Error al generar una factura");
			e.printStackTrace();

		}

	}

	public void onEndPage(PdfWriter writer, Document document) {

		PdfContentByte cb = writer.getDirectContent();

		Phrase pie = new Phrase(String.format("Página %d", writer.getCurrentPageNumber()));

		ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, pie,
				(document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 20, 0);

	}

}
