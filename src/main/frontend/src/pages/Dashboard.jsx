import Navbar from "../components/Navbar";
import MeetingList from "../components/MeetingList";

const Dashboard = (props) => {
  return (
    <>
      <Navbar updateAuth={props.updateAuth} />
      <MeetingList />
    </>
  );
};

export default Dashboard;
