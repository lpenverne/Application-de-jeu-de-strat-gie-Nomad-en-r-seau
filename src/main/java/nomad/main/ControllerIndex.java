package nomad.main;

/**
 * List all main controllers and assign them an index
 */
public enum ControllerIndex {
    LOGIN(0),
    SERVER_CONNECTION(1),
    MENU(2),
    CREATE_GAME(3),
    DIALOG(4),
    VIEW_GAME(5),
    WAITING_ROOM(6);

    public final int index;
    ControllerIndex(int index) {
        this.index = index;
    }
}