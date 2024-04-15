package com.eoc900;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.github.anastaciocintra.escpos.PrintModeStyle;
import com.github.anastaciocintra.output.PrinterOutputStream;
import com.github.anastaciocintra.escpos.Style;

import javax.print.PrintService;
import java.io.IOException;
import java.util.logging.Logger;

public class HelloWorld {
    public void justPrint(String printerName) throws IOException {
        // if (args.length != 1) {
        // System.out.println("Usage: java -jar escpos-simple.jar (\"printer name\")");
        // System.out.println("Printer list to use:");
        // String[] printServicesNames =
        // PrinterOutputStream.getListPrintServicesNames();
        // for (String printServiceName : printServicesNames) {
        // System.out.println(printServiceName);
        // }

        // System.exit(0);
        // }

        // PrintService printService =
        // PrinterOutputStream.getPrintServiceByName(printerName);

        // PrinterOutputStream printerOutputStream = new
        // PrinterOutputStream(printService);
        // printerOutputStream.close();
        // EscPos escpos = new EscPos(printerOutputStream);
        // escpos.writeLF("Hello world");
        // escpos.feed(5).cut(EscPos.CutMode.FULL);
        // escpos.close();

        try {
            String[] printServicesNames = PrinterOutputStream.getListPrintServicesNames();
            for (String printServiceName : printServicesNames) {
                System.out.println(printServiceName);
            }

            PrintService printService = PrinterOutputStream.getPrintServiceByName(printerName);
            PrinterOutputStream printerOutputStream = new PrinterOutputStream(printService);

            EscPos escpos = new EscPos(printerOutputStream);
            Style title = new Style()
                    .setFontSize(Style.FontSize._2, Style.FontSize._2)
                    .setJustification(EscPosConst.Justification.Center);

            PrintModeStyle normal = new PrintModeStyle();

            PrintModeStyle subtitle = new PrintModeStyle()
                    .setBold(true)
                    .setUnderline(true);

            PrintModeStyle bold = new PrintModeStyle()
                    .setBold(true);

            escpos.writeLF(title, "Consultorio ZENA")
                    .feed(1)
                    .write(normal, "Cliente: ")
                    .writeLF(subtitle, "Ramira Reyes")
                    .feed(1)
                    .writeLF(normal, "Estudio X   $1.00")
                    .writeLF(normal, "Estudion Y    $0.50")
                    .writeLF(normal, "Consulta   $700.50")
                    .writeLF(normal, "----------------")
                    .feed(1)
                    .writeLF(bold,
                            "TOTAL:           $701.50")
                    .writeLF(normal, "-----------------")
                    .feed(2)
                    .writeLF(normal, "En caso de requerir factura")
                    .writeLF(normal, "consulte www.ejemplo.com")
                    .feed(3)
                    .cut(EscPos.CutMode.FULL);

            escpos.close();
            // printerOutputStream.close();

        } catch (IOException ex) {
            System.out.println(ex);

        }

    }
}
