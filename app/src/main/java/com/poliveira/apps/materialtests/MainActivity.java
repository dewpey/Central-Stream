package com.poliveira.apps.materialtests;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.parse.Parse;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.cards.actions.BaseSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.TextSupplementalAction;
import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;


public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks {

    private Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "ISqKnMREV6c5nFN7F5flZVm7dn7s0vCwGvUXJDCa", "ydHnkOaaiot4iT7FaOwOdrnFfcu1QBwhn6YtwVUr");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);

 /*       ArrayList<Card> cards = new ArrayList<Card>();

        //Create a Card
        Card card = new Card(MainActivity.this);

        //Create a CardHeader
        CardHeader header = new CardHeader(MainActivity.this);

        String[] planets = new String[]{"Mercury", "Venus", "Earth", "Mars",
                "Jupiter", "Saturn", "Uranus", "Neptune"};

        header.setTitle(planets[1]);


        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(MainActivity.this, cards);

        CardListView listView = (CardListView) MainActivity.this.findViewById(R.id.myList);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }

        //Add Header
        // to card
        card.addCardHeader(header);
        header.setTitle(planets[1]);
        cards.add(card);
        header.setTitle(planets[2]);
        cards.add(card);
        header.setTitle(planets[3]);
        cards.add(card);
        cards.add(card);
        cards.add(card);*/
        // Set supplemental actions as text
        ArrayList<BaseSupplementalAction> actions = new ArrayList<BaseSupplementalAction>();

        // Set supplemental actions
        TextSupplementalAction t1 = new TextSupplementalAction(MainActivity.this, R.id.text1);
        t1.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(MainActivity.this," Click on Text SHARE ",Toast.LENGTH_SHORT).show();
            }
        });
        actions.add(t1);

        TextSupplementalAction t2 = new TextSupplementalAction(MainActivity.this, R.id.text1);
        t2.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(MainActivity.this," Click on Text LEARN ",Toast.LENGTH_SHORT).show();
            }
        });
        actions.add(t2);

        //Create a Card, set the title over the image and set the thumbnail
        MaterialLargeImageCard card =
                MaterialLargeImageCard.with(MainActivity.this)
                        .setTextOverImage("Italian Beaches")
                        .setTitle("This is my favorite local beach")
                        .setSubTitle("A wonderful place")
                        .useDrawableId(R.drawable.ic_launcher)
                        .setupSupplementalActions(R.layout.bigcard, actions)
                        .build();

        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(MainActivity.this," Click on ActionArea ",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }

    public void submitActivity(View v){
        startActivity(new Intent(MainActivity.this,SubmitActivity.class));
    }
}
