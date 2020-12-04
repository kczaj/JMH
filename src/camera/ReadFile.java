/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camera;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author Paweł
 */
public class ReadFile {

    private File file;
    private Scanner scanner;
    private FileReader fileReader;
    private BufferedReader bufferedReader;

    public ReadFile(String fileName) throws FileNotFoundException {
        this.file = new File(fileName);
        this.scanner = new Scanner(this.file);
        this.fileReader = new FileReader(this.file);
        this.bufferedReader = new BufferedReader(this.fileReader);
    }

    public ArrayList<Line3D> load() throws IOException {
        ArrayList<Line3D> lines = new ArrayList<>();
        String line;
        while (bufferedReader.readLine() != null) {
            line = scanner.next();
            double[] value = new double[6];
            StringTokenizer st = new StringTokenizer(line, ";");
            if (st.countTokens() == 6) {
                value[0] = Double.valueOf(st.nextToken());
                value[1] = Double.valueOf(st.nextToken());
                value[2] = Double.valueOf(st.nextToken());
                value[3] = Double.valueOf(st.nextToken());
                value[4] = Double.valueOf(st.nextToken());
                value[5] = Double.valueOf(st.nextToken());
            } else {
                throw new IOException("Wrong fommat of data!");
            }
            lines.add((new Line3D(new Point3D(value[0], value[1], value[2]), new Point3D(value[3], value[4], value[5]))));
        }
        return lines;
    }
}
