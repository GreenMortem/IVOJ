package com.example.vojdip.games

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vojdip.R
import com.example.vojdip.databinding.GameItemBinding

class GameAdapter(val listener: Listener):RecyclerView.Adapter<GameAdapter.GameHolder>() {
    val gameList = ArrayList<Game>()

    class GameHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = GameItemBinding.bind(item)

        fun bind(game: Game, listener : Listener) = with(binding){
            tVNameItem.text = game.nameGame
            tvRuleItem.text = game.ruleGame

            itemView.setOnClickListener{
                listener.onClick(game)
                tvRuleItem.visibility = if (tvRuleItem.visibility == View.VISIBLE){
                    View.GONE
                }else{
                    View.VISIBLE
                }
            }
        }
    }

    fun clear() {
        gameList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        return GameHolder(view)
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        holder.bind(gameList[position], listener)
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    fun addGame(game: Game){
        gameList.add(game)
        notifyDataSetChanged()
    }

    interface Listener{
        fun onClick(game: Game)

    }
}