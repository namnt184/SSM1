<h1>Add Staff</h1>
<?php
echo $this->Form->create('Staff');
echo $this->Form->input('name');
echo $this->Form->input('avatar');
echo $this->Form->input('location');
echo $this->Form->input('imei');
echo $this->Form->input('serial');
echo $this->Form->input('staffid');
echo $this->Form->end('Save Post');
?>