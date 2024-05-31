package comp3350.goodhabits.Logic;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class QuoteManager {

    //Поля
    public ArrayList<String> allQuotes = new ArrayList<>();
    private final Context quoteContext;
    private static boolean testMode = false;

    // Конструктор
    public QuoteManager(Context context){
        this.quoteContext = context;
        loadQuoteList();
    }

    //Этот метод загружает все кавычки в список массивов
    public void loadQuoteList(){
        AssetManager am = quoteContext.getAssets();

        try {
            InputStream is = am.open("Quotes.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null)
                allQuotes.add(line);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testMode(){
        testMode = true;
    }

    public void notTestMode(){
        testMode = false;
    }

    //возвращает значение quoteOfTheDay
    public String getQuote()
    {
        if(!testMode) {
            Random rand = new Random();
            int index = rand.nextInt(allQuotes.size());
            return allQuotes.get(index);
        }
        else{
            return "Это предложение предназначено для системного тестирования - DevTeam";
        }
    }
}
