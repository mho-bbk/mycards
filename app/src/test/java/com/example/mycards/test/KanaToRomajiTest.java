package com.example.mycards.test;

import com.example.mycards.jmdict.kanatoromaji.KanaToRomaji;

import org.junit.Assert;
import org.junit.Test;

public class KanaToRomajiTest {

    private final KanaToRomaji k2r = new KanaToRomaji();

    @Test
    public void translateKata() {
        String[] strs = {"ピュートフクジャガー",
                "マージャン",
                "タンヤオトイトイドラドラ",
                "キップ",
                "プリキュア",
                "シャーペン",
                "カプッ",
                "@マーク",
                "ティーカップ",
                "ビルディング",
                "トッツィ"};

        Assert.assertEquals("pyu-tofukujaga-", k2r.convert(strs[0]));
        Assert.assertEquals("ma-jan", k2r.convert(strs[1]));
        Assert.assertEquals("tanyaotoitoidoradora", k2r.convert(strs[2]));
        Assert.assertEquals("kippu", k2r.convert(strs[3]));
        Assert.assertEquals("purikyua", k2r.convert(strs[4]));
        Assert.assertEquals("sha-pen", k2r.convert(strs[5]));
        Assert.assertEquals("kapuッ", k2r.convert(strs[6]));
        Assert.assertEquals("@ma-ku", k2r.convert(strs[7]));
        Assert.assertEquals("ti-kappu", k2r.convert(strs[8]));
        Assert.assertEquals("birudingu", k2r.convert(strs[9]));
        Assert.assertEquals("tottsi", k2r.convert(strs[10]));
    }

    @Test
    public void translateHira() {
        String[] strs = {"あいうえお",
                "かきくけこ",
                "さしすせそ",
                "たちつてと",
                "なにぬねの",
                "はひふへほ",
                "まみむめも",
                "やゆよ",
                "らりるれろ",
                "わ",
                "を",
                "ん",
                "がぎぐげご",
                "ざじずぜぞ",
                "だぢづでど",
                "ばびぶべぼ",
                "ぱぴぷぺぽ",
                "きゃきゅきょ",
                "しゃしゅしょ",
                "ちゃちゅちょ",
                "にゃにゅにょ",
                "ひゃひゅひょ",
                "みゃみゅみょ",
                "りゃりゅりょ",
                "ぎゃぎゅぎょ",
                "じゃじゅじょ",
                "びゃびゅびょ",
                "ぴゃぴゅぴょ",
                "きてぃ"};

        Assert.assertEquals("aiueo", k2r.convert(strs[0]));
        Assert.assertEquals("kakikukeko", k2r.convert(strs[1]));
        Assert.assertEquals("sashisuseso", k2r.convert(strs[2]));
        Assert.assertEquals("tachitsuteto", k2r.convert(strs[3]));
        Assert.assertEquals("naninuneno", k2r.convert(strs[4]));
        Assert.assertEquals("hahifuheho", k2r.convert(strs[5]));
        Assert.assertEquals("mamimumemo", k2r.convert(strs[6]));
        Assert.assertEquals("yayuyo", k2r.convert(strs[7]));
        Assert.assertEquals("rarirurero", k2r.convert(strs[8]));
        Assert.assertEquals("wa", k2r.convert(strs[9]));
        Assert.assertEquals("wo", k2r.convert(strs[10]));
        Assert.assertEquals("n", k2r.convert(strs[11]));
        Assert.assertEquals("gagigugego", k2r.convert(strs[12]));
        Assert.assertEquals("zajizuzezo", k2r.convert(strs[13]));
        Assert.assertEquals("dadidudedo", k2r.convert(strs[14]));
        Assert.assertEquals("babibubebo", k2r.convert(strs[15]));
        Assert.assertEquals("papipupepo", k2r.convert(strs[16]));
        Assert.assertEquals("kyakyukyo", k2r.convert(strs[17]));
        Assert.assertEquals("shashusho", k2r.convert(strs[18]));
        Assert.assertEquals("chachucho", k2r.convert(strs[19]));
        Assert.assertEquals("nyanyunyo", k2r.convert(strs[20]));
        Assert.assertEquals("hyahyuhyo", k2r.convert(strs[21]));
        Assert.assertEquals("myamyumyo", k2r.convert(strs[22]));
        Assert.assertEquals("ryaryuryo", k2r.convert(strs[23]));
        Assert.assertEquals("gyagyugyo", k2r.convert(strs[24]));
        Assert.assertEquals("jajujo", k2r.convert(strs[25]));
        Assert.assertEquals("byabyubyo", k2r.convert(strs[26]));
        Assert.assertEquals("pyapyupyo", k2r.convert(strs[27]));
        Assert.assertEquals("kiti", k2r.convert(strs[28]));
    }

    @Test
    public void testDetailedSmallTsu() {
        String smallTsuInMiddle = "ロッポンギヒルズ";
        String smallTsuAtStart = "って";

        Assert.assertEquals("roppongihiruzu", k2r.convert(smallTsuInMiddle));
        Assert.assertEquals("tte", k2r.convert(smallTsuAtStart));
    }

    @Test
    public void testUnknownSymbols() {
        //If the Map doesn't contain the object
        String kyaa = "きゃぁーっ！";

        //Exception is caught within the class and an error message printed out
        //But the string is returned.
        String kyaaInLatin = k2r.convert(kyaa);
        System.out.println(kyaaInLatin);
    }

}