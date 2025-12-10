package ifsc.joe.Interfaces;

import ifsc.joe.domain.impl.Personagem;
import java.util.List;

public interface Guerreiro {
    void atacar();
    void atacarTodosProximos(List<Personagem> alvos);
}
