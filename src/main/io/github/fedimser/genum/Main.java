package io.github.fedimser.genum;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        GroupGenerator gen = new GroupGenerator();

        FileWriter writer = new FileWriter("groups.txt");

        for (int order = 1; order <= 9; order++) {
            System.out.println(order);

            Date start = new Date();
            writer.write("All finite groups of order " + String.valueOf(order) + ":\n");
            List<FinGroup> groups = gen.getAllGroups(order);

            int count = groups.size();
            writer.write("Count: " + String.valueOf(count) + "\n");

            for (FinGroup g : groups) {
                writer.write(g.writeMultTable() + "\n");
            }

            Date end = new Date();
            writer.write("Time elapsed: " + String.valueOf(end.getTime() - start.getTime()) + "ms.\n");
            writer.write("------------------------\n\n\n");
        }

        writer.close();
    }
}
