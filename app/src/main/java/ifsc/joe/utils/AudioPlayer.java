package ifsc.joe.utils;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class AudioPlayer {

    public static void playSound(String nomeArquivo) {
        new Thread(() -> {
            try {
                // Tenta carregar o recurso
                URL url = AudioPlayer.class.getClassLoader().getResource(nomeArquivo);
                if (url == null) {
                    System.err.println("Arquivo de som não encontrado: " + nomeArquivo);
                    return;
                }

                // Obtém o stream de áudio
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);

                // Obtém o Clip para carregar o áudio completamente
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);

                // Adiciona um Listener para fechar o Clip quando a reprodução terminar
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });

                // Reproduz o som
                clip.start();

            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                // Captura erros de formato ou de hardware
                System.err.println("Erro ao reproduzir som " + nomeArquivo + ": " + e.getMessage());
            }
        }).start();
    }
}