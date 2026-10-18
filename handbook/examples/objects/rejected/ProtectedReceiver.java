package q;

public class ProtectedReceiver extends p.Base {
    void inspect(p.Base base) {
        base.value = 1;
    }
}
