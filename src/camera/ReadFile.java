package camera;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

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

    public ArrayList<Figure> load() throws IOException {
        ArrayList<Figure> figures = new ArrayList<>();
        ArrayList<Line3D> lines = new ArrayList<>();
        String line, color = null;
        double[] values;
        while (bufferedReader.readLine() != null) {
            line = scanner.next();
            if (line.contains(";") == false) {
                if (lines.size() > 0) {
                    figures.add(new Figure(color, lines));
                    lines = new ArrayList<>();
                }
                color = line;
            } else {
                values = loadLine3D(line);
                lines.add((new Line3D(new Point3D(values[0], values[1], values[2]), new Point3D(values[3], values[4], values[5]))));
            }

        }
        figures.add(new Figure(color, lines));
        return figures;
    }

    private double[] loadLine3D(String line) throws IOException {
        double[] values = new double[6];
        StringTokenizer st = new StringTokenizer(line, ";");
        if (st.countTokens() == 6) {
            values[0] = Double.valueOf(st.nextToken());
            values[1] = Double.valueOf(st.nextToken());
            values[2] = Double.valueOf(st.nextToken());
            values[3] = Double.valueOf(st.nextToken());
            values[4] = Double.valueOf(st.nextToken());
            values[5] = Double.valueOf(st.nextToken());
        } else {
            throw new IOException("Wrong fommat of data!");
        }
        return values;
    }
}
