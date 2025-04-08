import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.farm2fabric.PaymentDetail
import com.example.farm2fabric.PaymentItem
import com.example.farm2fabric.R
import java.text.SimpleDateFormat
import java.util.*

class PaymentAdapter(
    private val context: Context,
    private val payments: List<PaymentItem>
) : RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>() {

    inner class PaymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val consignmentIdTextView: TextView = itemView.findViewById(R.id.consignmentIdTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val amountTextView: TextView = itemView.findViewById(R.id.amountTextView)
        val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_payment, parent, false)
        return PaymentViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val payment = payments[position]
        holder.consignmentIdTextView.text = payment.consignment_id

        val formatter = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val formattedDate = formatter.format(payment.date)
        holder.dateTextView.text = formattedDate

        holder.amountTextView.text = "₹${payment.amount}"
        holder.statusTextView.text = payment.status

        // Item click listener
        holder.itemView.setOnClickListener {
            val intent = Intent(context, PaymentDetail::class.java).apply {
                putExtra("consignment_id", payment.consignment_id)
                putExtra("amount", payment.amount.toString())
                putExtra("status", payment.status)
                putExtra("date", formattedDate)
                putExtra("order_id", payment.order_id)
                putExtra("payment_id", payment.paymentid)
                putExtra("quantity", payment.quantity.toString())
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = payments.size
}

