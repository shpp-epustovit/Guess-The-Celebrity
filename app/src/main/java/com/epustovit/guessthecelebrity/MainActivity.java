package com.epustovit.guessthecelebrity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {

    ArrayList<String> celebURLs = new ArrayList<String>();
    ArrayList<String> celebNames = new ArrayList<String>();

    int choseCeleb = 0;

    int locationOfCorrectAnswer = 0;
    String[] answers = new String[4];

    ImageView imageView;
    Button button;
    Button button2;
    Button button3;
    Button button4;

    public void celebChosen(View view) {
        // проверить какая кнопка нажата
        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            Toast.makeText(getApplicationContext(),"Correct", Toast.LENGTH_LONG).show();
        }else {

            Toast.makeText(getApplicationContext(),"Wrong! It was " + celebNames.get(choseCeleb), Toast.LENGTH_LONG).show();
        }

        createNewQuestion();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.imageView);

        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);

        DownloadTask task = new DownloadTask();
        String result = null;
        try {
            result = task.execute("http://www.posh24.se/kandisar").get();

            String[] splitResult = result.split("<div class=\"sidebarContainer\">");

            Pattern p = Pattern.compile("<img src=\"(.*?)\"");
            Matcher m = p.matcher(splitResult[0]);

            while(m.find()){

                celebURLs.add(m.group(1));
            }


            p = Pattern.compile("alt=\"(.*?)\"");
            m = p.matcher(splitResult[0]);

            while(m.find()){

                celebNames.add(m.group(1));
            }

            createNewQuestion();




        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void createNewQuestion(){
        Random random = new Random();
        choseCeleb = random.nextInt(celebURLs.size());

        ImageDownloader imageTask = new ImageDownloader();
        Bitmap celebImage;

        try {
            celebImage = imageTask.execute(celebURLs.get(choseCeleb)).get();
            imageView.setImageBitmap(celebImage);

            locationOfCorrectAnswer = random.nextInt(4);

            int incorectAnswerLocation;

            for (int i = 0; i < 4; i++){

                if (i == locationOfCorrectAnswer){
                    answers[i] = celebNames.get(choseCeleb);
                }else {

                    incorectAnswerLocation = random.nextInt(celebURLs.size());

                    while (incorectAnswerLocation == choseCeleb){
                        incorectAnswerLocation = random.nextInt(celebURLs.size());
                    }

                    answers[i] = celebNames.get(incorectAnswerLocation);
                }
            }

            button.setText(answers[0]);
            button2.setText(answers[1]);
            button3.setText(answers[2]);
            button4.setText(answers[3]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}
