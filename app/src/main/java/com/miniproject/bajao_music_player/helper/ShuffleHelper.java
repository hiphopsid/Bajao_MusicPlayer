package com.miniproject.bajao_music_player.helper;

import androidx.annotation.NonNull;

import com.miniproject.bajao_music_player.model.Song;

import java.util.Collections;
import java.util.List;

public class ShuffleHelper {

    public static void makeShuffleList(@NonNull List<Song> listToShuffle, final int current) {
        if (listToShuffle.isEmpty()) return;
        if (current >= 0) {
            Song song = listToShuffle.remove(current);
            Collections.shuffle(listToShuffle);
            listToShuffle.add(0, song);
        } else {
            Collections.shuffle(listToShuffle);
        }
    }
}
