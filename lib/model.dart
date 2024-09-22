class SDCard {
  final String path;
  final int total;
  final int free;
  SDCard(this.path, this.total,this.free);
  factory SDCard.fromJson(Map json) {
    return SDCard(json["path"], json["total"], json["free"]);
  }
}
