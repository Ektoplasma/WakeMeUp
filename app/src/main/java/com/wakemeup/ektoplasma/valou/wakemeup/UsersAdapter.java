package com.wakemeup.ektoplasma.valou.wakemeup;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Valentin on 03/08/2016.
 */
public class UsersAdapter extends BaseExpandableListAdapter{

    private Context ctx;
    private HashMap<String, List<String>> UsersCategory;
    private List<String> ListUsers;

    public UsersAdapter(Context ctx, HashMap<String, List<String>> UsersCategory, List<String> ListUsers)
    {
        this.ctx = ctx;
        this.UsersCategory = UsersCategory;
        this.ListUsers = ListUsers;
    }

    @Override
    public int getGroupCount() {
        return ListUsers.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return UsersCategory.get(ListUsers.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return ListUsers.get(i);
    }

    @Override
    public Object getChild(int parent, int child) {
        return UsersCategory.get(ListUsers.get(parent)).get(child);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int parent, int child) {
        return child;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertView, ViewGroup parentView) {
        String groupTitle = (String) getGroup(parent);
        if(convertView == null)
        {
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.parent_list, parentView, false);
        }
        TextView parentTextView = (TextView) convertView.findViewById(R.id.ParentTxt);
        parentTextView.setTypeface(null, Typeface.BOLD);
        parentTextView.setText(groupTitle);
        return convertView;
    }

    @Override
    public View getChildView(int parent, int child, boolean lastChild, View convertView, ViewGroup parentView) {
        String ChildTitle = (String) getChild(parent, child);
        if(convertView == null)
        {
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.child_list, parentView, false);
        }
        TextView child_textview = (TextView) convertView.findViewById(R.id.ChildTxt);
        child_textview.setText(ChildTitle);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
