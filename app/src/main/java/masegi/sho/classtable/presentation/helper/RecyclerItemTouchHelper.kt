package masegi.sho.classtable.presentation.helper

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import masegi.sho.classtable.presentation.adapter.TodoListAdapter

/**
 * Created by masegi on 2018/04/13.
 */

class RecyclerItemTouchHelper(
        private val dragDirection: Int,
        private val swipeDirection: Int,
        var listener: RecyclerItemTouchHelperListener
) : ItemTouchHelper.SimpleCallback(dragDirection, swipeDirection) {


    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {

        return viewHolder is TodoListAdapter.ItemViewHolder
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {

        if (viewHolder != null && viewHolder is TodoListAdapter.ItemViewHolder) {

            ItemTouchHelper.Callback.getDefaultUIUtil().onSelected(viewHolder.binding.itemForeground)
        }
    }

    override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

        if (viewHolder != null && viewHolder is TodoListAdapter.ItemViewHolder) {

            ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(
                    c,
                    recyclerView,
                    viewHolder.binding.itemForeground,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
            )
        }
    }

    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?) {

        if (viewHolder != null && viewHolder is TodoListAdapter.ItemViewHolder) {

            ItemTouchHelper.Callback.getDefaultUIUtil().clearView(viewHolder.binding.itemForeground)
        }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {

        if (viewHolder != null && viewHolder is TodoListAdapter.ItemViewHolder) {

            listener.onSwipe(viewHolder, direction, viewHolder.adapterPosition)
        }
    }

    interface RecyclerItemTouchHelperListener {

        fun onSwipe(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int)
    }
}