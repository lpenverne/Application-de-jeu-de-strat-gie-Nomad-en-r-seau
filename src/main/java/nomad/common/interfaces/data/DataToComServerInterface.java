package nomad.common.interfaces.data;

import nomad.common.data_structure.*;

import java.util.List;
import java.util.UUID;

public interface DataToComServerInterface {
    Game createGame(String name, UserLight host, int nbOfTowers, boolean spectAllowed, boolean spectChatAllowed, boolean hostColour);

    void joinGameRequest(Player player, GameLight game);

    void guestAccepted(GameLight game);

    void guestRefused(Player player);

    void addSpecInGame(UserLight user, GameLight game);

    List<User> getUserList(GameLight game);

    void launchGame(GameLight game);

    Tower saveTower(UserLight user, Tower t);

    Move saveMove(UserLight user, Move m);

    boolean checkGameEnded(GameLight game);

    Game getStoredGame(UUID gameId);

    void storeMessage(UUID gameId, Message message);

    List<Player> requestConnectedUserList();

    List<GameLight> requestGameList();

    void updateUserListAdd(User newUser);

    User updateUserListRemove(UUID userId);

    void updateListGamesRemove(User oldUser);

    User getUserProfile(UUID idUser);
}
