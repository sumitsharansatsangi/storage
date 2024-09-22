class SDCard {
  final String name;
  final int total;
  final int free;
  SDCard(this.name, this.total,this.free);
  factory SDCard.fromJson(Map json) {
    return SDCard(json["name"], json["total"], json["free"]);
  }
}
