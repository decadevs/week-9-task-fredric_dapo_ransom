package com.example.pokemon_app.Model

class Pokemon {
    /*
    public int id;
    public String num;
    public String name;
    public String img;
    public List<String> type;
    public String height;
    public String weight;
    public String candy;
    public int candy_count;
    public String egg;
    public double spawn_chance;
    public double avg_spawns;
    public String spawn_time;
    public List<double> multipliers;
    public List<String> weaknesses;
    public List<NextEvolution> next_evolution;
    public List<PrevEvolution> prev_evolution;
     */

    var id: Int = 0
    var num:String? = null
    var name:String? = null
    var img:String? = null
    var type:List<String>? = null
    var height:String? = null
    var weight:String? = null
    var candy:String? = null
    var candy_count:Int = 0
    var egg:String? = null
    var spawn_chance:Double = 0.toDouble()
    var svg_spawns:Double = 0.toDouble()
    var spawn_time:String? = null
    var multipliers:List<Double>? = null
    var weaknesses:List<String>? = null
    var next_evolution:List<Evolution>? = null
    var prev_evolution:List<Evolution>? = null
}