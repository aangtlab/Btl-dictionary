package com.example.btl_2023;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

/*
Bùi Tiến Thành X Quang Anh - Từ Điển Tiếng Anh - A+ hehehe
*/

public class Speech {

    public static void speak(String word) {
        if (word.equals("")) {
            return;
        }

        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");

        if (voice != null) {
            voice.allocate();
            boolean status = voice.speak(word);
            voice.deallocate();
        } else {
            System.out.println("Error: Unable to allocate voice.");
        }
    }
}
