package com.example.mycards.usecases;

import com.example.mycards.data.entities.JMDictEntry;
import com.example.mycards.data.repositories.DefaultJMDictRepository;
import com.example.mycards.data.repositories.JMDictRepository;
import com.example.mycards.usecases.jptranslate.GetJpWordsUseCase;
import com.example.mycards.usecases.jptranslate.jmdict.pojo.Kana;
import com.example.mycards.usecases.jptranslate.jmdict.pojo.Kanji;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class GetJpWordsUseCaseTest {

    private JMDictRepository mockedJmDictRepository;
    private GetJpWordsUseCase getJpWordsUseCase;

//    Reminder: constructor JMDictEntry(String gloss, String wordID, Kanji kanji, Kana kana,
//                       int glossCount, int glossOrder, int senseCount, int senseOrder,
//                       List<String> sensePosTags)

    //FAKE RESULTS: Teacher & related
    private JMDictEntry fakeEntryTeacher = new JMDictEntry("teacher", "1387990", new Kanji("先生"), new Kana("せんせい"),
            3, 1, 4, 1, new ArrayList<>(List.of("n")));
    private JMDictEntry fakeEntryInstructor = new JMDictEntry("instructor", "1022650", new Kanji(), new Kana("インストラクター"),
            1, 1, 1, 1, new ArrayList<>(List.of("n")));
    private JMDictEntry fakeEntrySchoolTeacher = null; //no result return

    //FAKE RESULTS: Tennis & related - for further tests
    private JMDictEntry fakeEntryTennis = new JMDictEntry("tennis", "1080000", new Kanji(), new Kana("テニス"),
            1, 1, 1, 1, new ArrayList<>(List.of("n")));
    private JMDictEntry fakeEntryLawnTennis = null; //no result return
    private JMDictEntry fakeEntryVolleyball = new JMDictEntry("volleyball", "1099960", new Kanji(), new Kana("バレーボール"),
            1, 1, 1, 1, new ArrayList<>(List.of("n")));

    //FAKE RESULTS: History & related - for further tests
    private JMDictEntry fakeEntryHistory = new JMDictEntry("history", "1558050", new Kanji("歴史"), new Kana("れきし"),
            1, 1, 1, 1, new ArrayList<>(List.of("n", "adj-no")));
    private JMDictEntry fakeEntryChronicle = new JMDictEntry("chronicle", "1045870", new Kanji(), new Kana("クロニクル"),
            1, 1, 1, 1, new ArrayList<>(List.of("n")));
    private JMDictEntry fakeEntryStory = new JMDictEntry("story", "2581590", new Kanji("お話"), new Kana("おはなし"),
            2, 1, 1, 1, new ArrayList<>(List.of("n")));

    @Before
    public void setUp() {
        //Mock the repository
        mockedJmDictRepository = Mockito.mock(DefaultJMDictRepository.class);
        //Instantiate the use case
        getJpWordsUseCase = new GetJpWordsUseCase(mockedJmDictRepository);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRunPasses() {
        //Set up fake db methods and results
        when(mockedJmDictRepository.getFirstJMDictEntry("teacher")).thenReturn(fakeEntryTeacher);
        when(mockedJmDictRepository.getFirstJMDictEntry("instructor")).thenReturn(fakeEntryInstructor);
        when(mockedJmDictRepository.getFirstJMDictEntry("schoolteacher")).thenReturn(fakeEntrySchoolTeacher);

        //Create the fake input: teacher (using list size 2)
        HashMap<String, List<String>> wordsForTranslation = new HashMap<>();
        wordsForTranslation.put("teacher", new ArrayList<>(List.of("instructor", "schoolteacher")));

        //Create the expected outcome based on input
        //NB: original input String should be in resultant HashMap
        //NB: where there is null return, nothing is put into the Map (NPE caught and String simply skipped)
        HashMap<String, HashMap<String, String>> expectedOutcome = new HashMap<>();
        HashMap<String, String> innerHashMap = new HashMap<>();
        innerHashMap.put("teacher", "先生 (せんせい, sensei)");
        innerHashMap.put("instructor", "インストラクター (insutorakuta-)");
        expectedOutcome.put("teacher", innerHashMap);

        //Run, indirectly test private methods
        HashMap<String, HashMap<String, String>> mockRunResults = getJpWordsUseCase.run(wordsForTranslation);

        assertEquals(expectedOutcome, mockRunResults);;
        assertEquals(innerHashMap, mockRunResults.get("teacher"));
        assertEquals(2, mockRunResults.get("teacher").size());
    }

    @Test
    public void testRun_NoJMDictEntries_ReturnsStringKeyAndEmptyInnerHashMapValue() {
        //Set up scenario where there is no jmdict entry in the local db
        when(mockedJmDictRepository.getFirstJMDictEntry(anyString())).thenReturn(null);

        //Create fake input (nonsensical)
        HashMap<String, List<String>> wordsForTranslation = new HashMap<>();
        wordsForTranslation.put("nonsense input string", new ArrayList<>());

        //Create expected outcome from run()
        //NB: original input String should be in resultant HashMap
        //NB: where there is null return, nothing is put into the Map (NPE caught and String simply skipped)
        HashMap<String, HashMap<String, String>> expectedOutcome = new HashMap<>();
        HashMap<String, String> innerHashMap = new HashMap<>();
        expectedOutcome.put("nonsense input string", innerHashMap);

        //Run, indirectly test private methods
        HashMap<String, HashMap<String, String>> mockRunResults = getJpWordsUseCase.run(wordsForTranslation);

        assertEquals(expectedOutcome, mockRunResults);
        assertNotNull(mockRunResults.get("nonsense input string"));
        assertTrue(mockRunResults.get("nonsense input string").isEmpty());
        assertEquals(innerHashMap, mockRunResults.get("nonsense input string"));
    }
}