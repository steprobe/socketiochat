package com.steware.socketiochatapp;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.steoware.webscokettesting.R;

public class MainFragment extends Fragment implements WebSocketServiceCallback {

    private WebSocketServiceProxy mServiceProxy;
    private TextView mMessages;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mServiceProxy = new WebSocketServiceProxy(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        mServiceProxy.bind(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mServiceProxy.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mMessages = (TextView) view.findViewById(R.id.messages);
        mMessages.setText("Connecting....");
        final TextView entry = (EditText)view.findViewById(R.id.textEntry);
        view.findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServiceProxy.sendMessage(entry.getText().toString());
                entry.setText("");
            }
        });

        return view;
    }

    @Override
    public void onIsTypingMapUpdated(Object isTypingEvents) {

    }

    @Override
    public void onNewTypingEventReceived(String str, String str2, String str3, Integer num, Boolean bool) {

    }

    @Override
    public void onNewWebSocketMessageReceived(String message) {
        mMessages.append("\n" + message);
    }

    @Override
    public void onUserUpdated(String str) {

    }

    @Override
    public void onWebSocketConnected() {
        mMessages.append("\nConnected to socket.io chat service");
    }

    @Override
    public void onWebSocketDisconnected() {

    }
}
