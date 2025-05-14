package teik.ers.global.mgs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateCheckerMG {

    private final int resourceId;

    public UpdateCheckerMG(int resourceId) {
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        try (InputStream is = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId + "/~")
                .openStream(); Scanner scann = new Scanner(is)) {
            if (scann.hasNext()) {
                consumer.accept(scann.next());
            }
        } catch (IOException e) {
            System.out.println(
                    "[EpicReports] Unable to check updates: " + e.getMessage()
            );
        }
    }
}
