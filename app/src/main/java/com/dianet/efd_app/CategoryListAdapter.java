package com.dianet.efd_app;

/**
 * Created by BELLO on 16/02/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class CategoryListAdapter extends BaseExpandableListAdapter {

    public static String categorytype = null;
    private final SparseArray<Group> groups;
    public LayoutInflater inflater;
    public Activity activity;
    protected static final int REQUEST_CODE6 = 5;

    public CategoryListAdapter(Activity act, SparseArray<Group> groups) {
        activity = act;
        this.groups = groups;
        inflater = act.getLayoutInflater();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).children.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String children = (String) getChild(groupPosition, childPosition);
        TextView text = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.category_listrow_details, null);
        }
        text = (TextView) convertView.findViewById(R.id.textView1);
        text.setText(children);
        final EditText EditTag = (EditText) convertView.findViewById(R.id.edit_tag);
        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(activity, children,
                     //   Toast.LENGTH_SHORT).show();
                Toast.makeText(activity, "You Selected" + " " + children + " " + "Category",
                        Toast.LENGTH_LONG).show();
                //EditText EditTag = (EditText) convertView.findViewById(R.id.edit_tag);
                //EditText EditTag = (EditText) findViewById(R.id.edit_tag);
                //EditTag.setText(children);
                categorytype = children;
                Context context = v.getContext();

                Intent intent3 = new Intent(context, MainActivity.class);
               // Intent intent3 = new Intent(context, HomeFragment.class);
                intent3.putExtra("categorytype",children);
                context.startActivity(intent3);

            }


        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).children.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.category_listrow_group, null);
        }
        final Group group = (Group) getGroup(groupPosition);
        ((TextView) convertView).setText(group.string);

        //((TextView) convertView).setText(isExpanded);
        if(group.string == "Tithes"){
            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(activity, "You Selected" + " " + group.string + " " + "Category",
                            Toast.LENGTH_LONG).show();
                    categorytype = group.string;
                    Context context = v.getContext();

                    Intent intent3 = new Intent(context, MainActivity.class);
                    intent3.putExtra("categorytype",group.string);
                    context.startActivity(intent3);


                }
             //   Intent intent = new Intent(this, TagsByCategory.class);

            });
        }else if (group.string == "Taxes"){

            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(activity, "You Selected" + " " + group.string + " " + "Category",
                            Toast.LENGTH_LONG).show();
                    categorytype = group.string;
                    Context context = v.getContext();

                    Intent intent3 = new Intent(context, MainActivity.class);
                    intent3.putExtra("categorytype",group.string);
                    context.startActivity(intent3);


                }
                //   Intent intent = new Intent(this, TagsByCategory.class);

            });
        }
        else if (group.string == "Rent/Mortgage"){

            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(activity, "You Selected" + " " + group.string + " " + "Category",
                            Toast.LENGTH_LONG).show();
                    categorytype = group.string;
                    Context context = v.getContext();

                    Intent intent3 = new Intent(context, MainActivity.class);
                    intent3.putExtra("categorytype",group.string);
                    context.startActivity(intent3);


                }
                //   Intent intent = new Intent(this, TagsByCategory.class);

            });
        }
        else if (group.string == "Savings"){

            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(activity, "You Selected" + " " + group.string + " " + "Category",
                            Toast.LENGTH_LONG).show();
                    categorytype = group.string;
                    Context context = v.getContext();

                    Intent intent3 = new Intent(context, MainActivity.class);
                    intent3.putExtra("categorytype",group.string);
                    context.startActivity(intent3);


                }
                //   Intent intent = new Intent(this, TagsByCategory.class);

            });
        }

        else if (group.string == "Food"){

            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(activity, group.string,
                            Toast.LENGTH_LONG).show();
                    categorytype = "You Selected" + " " + group.string + " " + "Category";
                    Context context = v.getContext();

                    Intent intent3 = new Intent(context, MainActivity.class);
                    intent3.putExtra("categorytype",group.string);
                    context.startActivity(intent3);


                }
                //   Intent intent = new Intent(this, TagsByCategory.class);

            });
        }
        else if (group.string == "School/Childcare"){

            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(activity, "You Selected" + " " + group.string + " " + "Category",
                            Toast.LENGTH_LONG).show();
                    categorytype = group.string;
                    Context context = v.getContext();

                    Intent intent3 = new Intent(context, MainActivity.class);
                    intent3.putExtra("categorytype",group.string);
                    context.startActivity(intent3);


                }
                //   Intent intent = new Intent(this, TagsByCategory.class);

            });
        }

        else if (group.string == "Entertainment"){

            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(activity, "You Selected" + " " + group.string + " " + "Category",
                            Toast.LENGTH_LONG).show();
                    categorytype = group.string;
                    Context context = v.getContext();

                    Intent intent3 = new Intent(context, MainActivity.class);
                    intent3.putExtra("categorytype",group.string);
                    context.startActivity(intent3);


                }
                //   Intent intent = new Intent(this, TagsByCategory.class);

            });
        }

        else if (group.string == "Debts"){

            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(activity, "You Selected" + " " + group.string + " " + "Category",
                            Toast.LENGTH_LONG).show();
                    categorytype = group.string;
                    Context context = v.getContext();

                    Intent intent3 = new Intent(context, MainActivity.class);
                    intent3.putExtra("categorytype",group.string);
                    context.startActivity(intent3);


                }
                //   Intent intent = new Intent(this, TagsByCategory.class);

            });
        }

        else if (group.string == "Clothing"){

            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(activity, "You Selected" + " " + group.string + " " + "Category",
                            Toast.LENGTH_LONG).show();
                    categorytype = group.string;
                    Context context = v.getContext();

                    Intent intent3 = new Intent(context, MainActivity.class);
                    intent3.putExtra("categorytype",group.string);
                    context.startActivity(intent3);


                }
                //   Intent intent = new Intent(this, TagsByCategory.class);

            });
        }

        else if (group.string == "Medical"){

            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(activity, "You Selected" + " " + group.string + " " + "Category",
                            Toast.LENGTH_LONG).show();
                    categorytype = group.string;
                    Context context = v.getContext();

                    Intent intent3 = new Intent(context, MainActivity.class);
                    intent3.putExtra("categorytype",group.string);
                    context.startActivity(intent3);


                }
                //   Intent intent = new Intent(this, TagsByCategory.class);

            });
        }
        /*convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, group.string,
                        Toast.LENGTH_SHORT).show();
            }
        });*/
        return convertView;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
