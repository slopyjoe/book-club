package models

sealed trait Vote{
  def answer:String
}
case object YesVote extends Vote {
  def answer = "Yes"
}

case object NoVote extends Vote {
  def answer = "No"
}

case class BookOfInterest(book:Book, bookClub:BookClub, votes:Seq[Vote], suggestedBy:User)
