package snapshot;

import java.util.Scanner;

public class ApplicationMain {
    public static void main(String[] args) {
        InputText inputText = new InputText();
        SnapshotHolder snapshotsHolder = new SnapshotHolder();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            if (input.equals(":list")) {
                System.out.println(inputText.getText()); }
            else if (input.equals(":undo")) {
                Snapshot snapshot = snapshotsHolder.popTextText();
                inputText.restoreSnapshot(snapshot); }
            else {
                snapshotsHolder.pushTextText(inputText.createSnapshot());
                inputText.append(input);
            }
        }
    }
}
