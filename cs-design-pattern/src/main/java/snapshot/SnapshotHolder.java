package snapshot;

import java.util.Stack;

public class SnapshotHolder {

    private Stack<Snapshot> snapshots = new Stack<>();

    public Snapshot popTextText(){
        return snapshots.pop();
    }

    public void pushTextText(Snapshot text){
        // deepClonedInputText
        snapshots.push(text);
    }
}
