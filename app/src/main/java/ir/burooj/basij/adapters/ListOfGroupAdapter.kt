package ir.burooj.basij.adapters

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ir.burooj.basij.GroupShowActivity
import ir.burooj.basij.R
import ir.burooj.basij.apiClass.GroupL

class ListOfGroupAdapter(private val myDataSet: List<GroupL>, private val picasso: Picasso) :
        RecyclerView.Adapter<ListOfGroupAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View, val context: Context, private val picasso: Picasso) : RecyclerView.ViewHolder(itemView) {
        private val textName = itemView.findViewById<TextView>(R.id.textView15)
        private val parent = itemView.findViewById<ConstraintLayout>(R.id.base_parent)
        private val imageView = itemView.findViewById<ImageView>(R.id.image_view_group2)
        fun bind(position: Int, groupL: GroupL) {
            picasso.load("https://burooj.ir/${groupL.profile_image_path}")
                    .placeholder(R.drawable.circle_for_image_view_2)
                    .error(R.drawable.errorpic)
                    .into(imageView)
            textName.text = groupL.groupName
            val tf = Typeface.createFromAsset(context.assets, "lalezar.ttf")
            //textName.typeface = tf
            parent.setOnClickListener(View.OnClickListener {

                val intent = Intent(context, GroupShowActivity::class.java)
                intent.putExtra("id", groupL.id)
                intent.putExtra("name", groupL.groupName)
                context.startActivity(intent)
            })
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ListOfGroupAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_group, parent, false)
        return MyViewHolder(view, parent.context, picasso)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(position, myDataSet[position])
    }


    override fun getItemCount() = myDataSet.size
}

