package com.example.mycards.usecases.jptranslate.jmdict.kanatoromaji;

import android.util.Log;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KanaToRomaji {

    private static final String TAG = "KanaToRomaji";

    private static final Map<String, String> KANA_ROMAJI_MAP;

    static {
        Map<String, String> tempMap = new HashMap<>();

        tempMap.put("ア", "a");
        tempMap.put("イ", "i");
        tempMap.put("ウ", "u");
        tempMap.put("エ", "e");
        tempMap.put("オ", "o");
        tempMap.put("あ", "a");
        tempMap.put("い", "i");
        tempMap.put("う", "u");
        tempMap.put("え", "e");
        tempMap.put("お", "o");

        tempMap.put("カ", "ka");
        tempMap.put("キ", "ki");
        tempMap.put("ク", "ku");
        tempMap.put("ケ", "ke");
        tempMap.put("コ", "ko");
        tempMap.put("か", "ka");
        tempMap.put("き", "ki");
        tempMap.put("く", "ku");
        tempMap.put("け", "ke");
        tempMap.put("こ", "ko");

        tempMap.put("サ", "sa");
        tempMap.put("シ", "shi");
        tempMap.put("ス", "su");
        tempMap.put("セ", "se");
        tempMap.put("ソ", "so");
        tempMap.put("さ", "sa");
        tempMap.put("し", "shi");
        tempMap.put("す", "su");
        tempMap.put("せ", "se");
        tempMap.put("そ", "so");

        tempMap.put("タ", "ta");
        tempMap.put("チ", "chi");
        tempMap.put("ツ", "tsu");
        tempMap.put("テ", "te");
        tempMap.put("ト", "to");
        tempMap.put("た", "ta");
        tempMap.put("ち", "chi");
        tempMap.put("つ", "tsu");
        tempMap.put("て", "te");
        tempMap.put("と", "to");

        tempMap.put("ナ", "na");
        tempMap.put("ニ", "ni");
        tempMap.put("ヌ", "nu");
        tempMap.put("ネ", "ne");
        tempMap.put("ノ", "no");
        tempMap.put("な", "na");
        tempMap.put("に", "ni");
        tempMap.put("ぬ", "nu");
        tempMap.put("ね", "ne");
        tempMap.put("の", "no");

        tempMap.put("ハ", "ha");
        tempMap.put("ヒ", "hi");
        tempMap.put("フ", "fu");
        tempMap.put("ヘ", "he");
        tempMap.put("ホ", "ho");
        tempMap.put("は", "ha");
        tempMap.put("ひ", "hi");
        tempMap.put("ふ", "fu");
        tempMap.put("へ", "he");
        tempMap.put("ほ", "ho");

        tempMap.put("マ", "ma");
        tempMap.put("ミ", "mi");
        tempMap.put("ム", "mu");
        tempMap.put("メ", "me");
        tempMap.put("モ", "mo");
        tempMap.put("ま", "ma");
        tempMap.put("み", "mi");
        tempMap.put("む", "mu");
        tempMap.put("め", "me");
        tempMap.put("も", "mo");

        tempMap.put("ヤ", "ya");
        tempMap.put("ユ", "yu");
        tempMap.put("ヨ", "yo");
        tempMap.put("や", "ya");
        tempMap.put("ゆ", "yu");
        tempMap.put("よ", "yo");

        tempMap.put("ラ", "ra");
        tempMap.put("リ", "ri");
        tempMap.put("ル", "ru");
        tempMap.put("レ", "re");
        tempMap.put("ロ", "ro");
        tempMap.put("ら", "ra");
        tempMap.put("り", "ri");
        tempMap.put("る", "ru");
        tempMap.put("れ", "re");
        tempMap.put("ろ", "ro");

        tempMap.put("ワ", "wa");
        tempMap.put("ヲ", "wo");
        tempMap.put("ン", "n");
        tempMap.put("わ", "wa");
        tempMap.put("を", "wo");
        tempMap.put("ん", "n");

        tempMap.put("ガ", "ga");
        tempMap.put("ギ", "gi");
        tempMap.put("グ", "gu");
        tempMap.put("ゲ", "ge");
        tempMap.put("ゴ", "go");
        tempMap.put("が", "ga");
        tempMap.put("ぎ", "gi");
        tempMap.put("ぐ", "gu");
        tempMap.put("げ", "ge");
        tempMap.put("ご", "go");

        tempMap.put("ザ", "za");
        tempMap.put("ジ", "ji");
        tempMap.put("ズ", "zu");
        tempMap.put("ゼ", "ze");
        tempMap.put("ゾ", "zo");
        tempMap.put("ざ", "za");
        tempMap.put("じ", "ji");
        tempMap.put("ず", "zu");
        tempMap.put("ぜ", "ze");
        tempMap.put("ぞ", "zo");

        tempMap.put("ダ", "da");
        tempMap.put("ヂ", "di");
        tempMap.put("ヅ", "du");
        tempMap.put("デ", "de");
        tempMap.put("ド", "do");
        tempMap.put("だ", "da");
        tempMap.put("ぢ", "di");
        tempMap.put("づ", "du");
        tempMap.put("で", "de");
        tempMap.put("ど", "do");

        tempMap.put("バ", "ba");
        tempMap.put("ビ", "bi");
        tempMap.put("ブ", "bu");
        tempMap.put("ベ", "be");
        tempMap.put("ボ", "bo");
        tempMap.put("ば", "ba");
        tempMap.put("び", "bi");
        tempMap.put("ぶ", "bu");
        tempMap.put("べ", "be");
        tempMap.put("ぼ", "bo");

        tempMap.put("パ", "pa");
        tempMap.put("ピ", "pi");
        tempMap.put("プ", "pu");
        tempMap.put("ペ", "pe");
        tempMap.put("ポ", "po");
        tempMap.put("ぱ", "pa");
        tempMap.put("ぴ", "pi");
        tempMap.put("ぷ", "pu");
        tempMap.put("ぺ", "pe");
        tempMap.put("ぽ", "po");

        tempMap.put("キャ", "kya");
        tempMap.put("キュ", "kyu");
        tempMap.put("キョ", "kyo");
        tempMap.put("きゃ", "kya");
        tempMap.put("きゅ", "kyu");
        tempMap.put("きょ", "kyo");

        tempMap.put("シャ", "sha");
        tempMap.put("シェ", "she");
        tempMap.put("シュ", "shu");
        tempMap.put("ショ", "sho");
        tempMap.put("しゃ", "sha");
        tempMap.put("しぇ", "she");
        tempMap.put("しゅ", "shu");
        tempMap.put("しょ", "sho");

        tempMap.put("チャ", "cha");
        tempMap.put("チュ", "chu");
        tempMap.put("チョ", "cho");
        tempMap.put("ちゃ", "cha");
        tempMap.put("ちゅ", "chu");
        tempMap.put("ちょ", "cho");

        tempMap.put("ニャ", "nya");
        tempMap.put("ニュ", "nyu");
        tempMap.put("ニョ", "nyo");
        tempMap.put("にゃ", "nya");
        tempMap.put("にゅ", "nyu");
        tempMap.put("にょ", "nyo");

        tempMap.put("ミャ", "mya");
        tempMap.put("ミュ", "myu");
        tempMap.put("ミョ", "myo");
        tempMap.put("みゃ", "mya");
        tempMap.put("みゅ", "myu");
        tempMap.put("みょ", "myo");

        tempMap.put("ヒャ", "hya");
        tempMap.put("ヒュ", "hyu");
        tempMap.put("ヒョ", "hyo");
        tempMap.put("ひゃ", "hya");
        tempMap.put("ひゅ", "hyu");
        tempMap.put("ひょ", "hyo");

        tempMap.put("リャ", "rya");
        tempMap.put("リュ", "ryu");
        tempMap.put("リョ", "ryo");
        tempMap.put("りゃ", "rya");
        tempMap.put("りゅ", "ryu");
        tempMap.put("りょ", "ryo");

        tempMap.put("ギャ", "gya");
        tempMap.put("ギュ", "gyu");
        tempMap.put("ギョ", "gyo");
        tempMap.put("ぎゃ", "gya");
        tempMap.put("ぎゅ", "gyu");
        tempMap.put("ぎょ", "gyo");

        tempMap.put("ジャ", "ja");
        tempMap.put("ジュ", "ju");
        tempMap.put("ジョ", "jo");
        tempMap.put("じゃ", "ja");
        tempMap.put("じゅ", "ju");
        tempMap.put("じょ", "jo");

        tempMap.put("ティ", "ti");
        tempMap.put("ディ", "di");
        tempMap.put("てぃ", "ti");
        tempMap.put("でぃ", "di");

        tempMap.put("ツィ", "tsi");
        tempMap.put("ヂャ", "dya");
        tempMap.put("ヂュ", "dyu");
        tempMap.put("ヂョ", "dyo");
        tempMap.put("つぃ", "tsi");
        tempMap.put("ぢゃ", "dya");
        tempMap.put("ぢゅ", "dyu");
        tempMap.put("ぢょ", "dyo");

        tempMap.put("ビャ", "bya");
        tempMap.put("ビュ", "byu");
        tempMap.put("ビョ", "byo");
        tempMap.put("ピャ", "pya");
        tempMap.put("ピュ", "pyu");
        tempMap.put("ピョ", "pyo");
        tempMap.put("びゃ", "bya");
        tempMap.put("びゅ", "byu");
        tempMap.put("びょ", "byo");
        tempMap.put("ぴゃ", "pya");
        tempMap.put("ぴゅ", "pyu");
        tempMap.put("ぴょ", "pyo");

        tempMap.put("ー", "-");

        tempMap.put("チェ", "che");
        tempMap.put("フィ", "fi");
        tempMap.put("フェ", "fe");
        tempMap.put("フォ", "fo");
        tempMap.put("ちぇ", "che");
        tempMap.put("ふぃ", "fi");
        tempMap.put("ふぇ", "fe");
        tempMap.put("ふぉ", "fo");

        tempMap.put("ウィ", "wi");
        tempMap.put("ウェ", "we");
        tempMap.put("ヴィ", "vi");
        tempMap.put("ヴェ", "ve");
        tempMap.put("うぃ", "wi");
        tempMap.put("うぇ", "we");
        tempMap.put("ヴぃ", "vi");
        tempMap.put("ヴぇ", "ve");

        KANA_ROMAJI_MAP = Collections.unmodifiableMap(tempMap);
    }

    public String convert(String s) {
        StringBuilder t = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            //the 2 here is to account for the combinations eg gya, kya, which are two chars but one syllable
            if (i <= s.length() - 2) {
                if (KANA_ROMAJI_MAP.containsKey(s.substring(i, i + 2))) {
                    t.append(KANA_ROMAJI_MAP.get(s.substring(i, i + 2)));
                    i++;
                } else if (KANA_ROMAJI_MAP.containsKey(s.substring(i, i + 1))) {
                    t.append(KANA_ROMAJI_MAP.get(s.substring(i, i + 1)));
                } else if (s.charAt(i) == 'ッ' || s.charAt(i) == 'っ') {
                    if(i == s.length() - 1) {
                        //if small tsu appears at the end of the string, do nothing
                        break;
                    } else {
                        //Factor in small tsu to mean repeat next consonant
                        try {
                            t.append(KANA_ROMAJI_MAP.get(s.substring(i + 1, i + 2)).charAt(0));
                        } catch (NullPointerException e) {
                            Log.d(TAG, "NPE occurred trying to retrieve char after small tsu");
                            //do not throw the exception, as we still want what's left of the romanised string to return
                        }
                    }
                } else {
                    t.append(s.charAt(i));
                }
            } else {
                if (KANA_ROMAJI_MAP.containsKey(s.substring(i, i + 1))) {
                    t.append(KANA_ROMAJI_MAP.get(s.substring(i, i + 1)));
                } else {
                    t.append(s.charAt(i));
                }
            }
        }
        return t.toString();
    }

    public static Map<String, String> getDictionary() {
        return KANA_ROMAJI_MAP;
    }

}
