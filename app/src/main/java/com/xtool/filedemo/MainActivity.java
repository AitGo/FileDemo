package com.xtool.filedemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xtool.filedemo.entity.Dir;
import com.xtool.filedemo.entity.DirUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lv_dir;
    private Map<String,List<Dir>> dirMap;
    private DirListAdapter adapter;
    private List<Dir> roots;
    private int backPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    public List<Dir> getRootList() {
        return dirMap.get(Dir.ROOTPATH);
    }

    private void initData() {
        try {
            InputStream inputStream = getAssets().open("config.xml");
            dirMap =  DirUtils.pullDirXml(inputStream);
            roots = getRootList();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        lv_dir = (ListView) findViewById(R.id.lv_dir);
        lv_dir.setOnItemClickListener(this);
        adapter = new DirListAdapter(this,dirMap.get(Dir.ROOTPATH));
        lv_dir.setAdapter(adapter);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String parentName = adapter.getDirName(position);

        if(parentName.equals("...")) {
            adapter.setDirs(adapter.getChildrenList(position));
            adapter.notifyDataSetChanged();
            adapter.getDirs().get(backPosition).getChildren().remove(0);
            return;
        }
        List<Dir> childrenList = dirMap.get(parentName);

        if(childrenList != null) {
            List<Dir> oldDirs = adapter.getDirs();
            if(oldDirs != null && !oldDirs.get(position).getChildren().get(0).getDirName().equals("...")) {
                backPosition = position;
                Dir back = new Dir();
                back.setChildren(oldDirs);
                back.setDirName("...");
                childrenList.add(0,back);
            }
        }
        if(childrenList != null && childrenList.size() > 0) {
            adapter.setDirs(childrenList);
            adapter.notifyDataSetChanged();
        }else {
            //到达叶子节点，隐藏listview，显示webview

        }

    }


}
