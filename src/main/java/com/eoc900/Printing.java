package com.eoc900;

import javax.print.PrintService;
import java.io.IOException;
import java.util.logging.Logger;

import com.eoc900.classes.Multidimentional;
import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.github.anastaciocintra.escpos.PrintModeStyle;
import com.github.anastaciocintra.output.PrinterOutputStream;
import com.github.anastaciocintra.escpos.Style;

public class Printing {
    public String printerName;
    public String mainTitle;
    public String patientName;
    public String location;
    public String date;
    public String telephone;
    public String urlFactura;
    public String[][] servicesC;
    public Float total;

    public Printing(String mainTitle, String patient, String location, String telephone, String date, String[][] data,
            Float total) {
        this.mainTitle = mainTitle;
        this.patientName = patient;
        this.location = location;
        this.telephone = telephone;
        this.date = date;
        this.servicesC = data;
        this.total = total;
    }

    public void setPrinterName(String name) {
        this.printerName = name;
    }

    // TEST PENDING
    public String[] returnPrintersAvailable() {
        String[] printServicesNames;
        try {
            printServicesNames = PrinterOutputStream.getListPrintServicesNames();
            return printServicesNames;
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }

    public void print(String[][] service) throws IOException {
        PrintService printService = PrinterOutputStream.getPrintServiceByName(this.printerName);
        PrinterOutputStream printerOutputStream = new PrinterOutputStream(printService);
        EscPos escpos = new EscPos(printerOutputStream);
        Style title = new Style()
                .setFontSize(Style.FontSize._2, Style.FontSize._2)
                .setJustification(EscPosConst.Justification.Center);

        // Styles
        PrintModeStyle normal = new PrintModeStyle();

        PrintModeStyle subtitle = new PrintModeStyle()
                .setBold(true)
                .setUnderline(true);

        PrintModeStyle bold = new PrintModeStyle()
                .setBold(true);

        escpos.writeLF(title, this.mainTitle)
                .feed(1)
                .write(normal, "Cliente: ")
                .writeLF(subtitle, this.patientName).feed(1);

        for (int i = 0; i < service.length; i++) {
            escpos.writeLF(normal,
                    Multidimentional.splitWord(servicesC[i][7], 2) + " " + servicesC[i][8] + " " + servicesC[i][9]);
        }

        escpos.writeLF(normal, "----------------")
                .feed(1)
                .writeLF(bold,
                        "TOTAL:           $" + total)
                .writeLF(normal, "-----------------")
                .feed(2)
                .writeLF(normal, "En caso de requerir factura")
                .writeLF(normal, "consulte www.ejemplo.com")
                .feed(3)
                .cut(EscPos.CutMode.FULL);

        escpos.close();

    }
}